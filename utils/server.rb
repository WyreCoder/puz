require 'securerandom'
require 'digest/sha2'
require 'json'
require 'sinatra'
require 'sinatra/reloader'
require 'mysql2'
require 'yaml'

require './challenge_helper'

$config = YAML::load(File.read('config.yml'))
set :bind, '0.0.0.0'

get '/' do
	json({
		:success => 1
	})
end

get '/api/v1/test' do
	json({
		:success => 1
	})
end

post '/api/v1/login' do
	username, password = params[:username], params[:password]

	res = query('SELECT * FROM `users` WHERE username=? AND password=?;', username, hash!(password))

	return halt!('Bad credentials') if res.length != 1
	user = res[0]

	token = SecureRandom.uuid
	query('UPDATE `users` SET token=? WHERE id=?;', token, user['id'])

	json({
		:success => 1,
		:username => user['username'],
		:id => user['id'],
		:token => token,
		:score => user['score'],
		:health => [user['health'], user['health_total']]
	})
end

get '/api/v1/scores' do 
	user = authorize!

	json({
		:username => user['username'],
		:id => user['id'],
		:score => user['score'],
		:health => [user['health'], user['health_total']]
	})
end

get '/api/v1/search' do
	user = authorize!

	latitude, longitude = params[:latitude].to_f, params[:longitude].to_f

	# Holy shit. This query does lots of clever geotagging stuff. 
	res = query('SELECT c.id, c.type, c.meta, u.completed AS completed,
			slc( ?, ?, y(geom),x(geom)) as distance_in_meters,
			X(geom) AS latitude, Y(geom) AS longitude FROM `challenges` c 
		LEFT JOIN `user_challenges` u
		ON c.id = u.challenge_id
		WHERE MBRContains(envelope(linestring(point(( ? +(2/111)), ( ? +(2/111))), point(( ? -(2/111)), ( ? -(2/111))))), geom)',
		latitude, longitude, latitude, longitude, latitude, longitude)


	res.map! { | row | {
		'challenge_id' => row['id'],
		'type' => row['type'],
		'complete' => row['completed'] ? true : false,
		'latitude' => row['latitude'],
		'longitude' => row['longitude'],
		'distance' => row['distance_in_meters'] }.merge(JSON.parse(row['meta'])) }

	json({
		'latitude' => latitude,
		'longitude' => longitude,
		'items' => res
	})

end

get '/api/v1/challenge' do

	user = authorize!
	challenge_id = Integer(params[:challenge_id]) rescue halt!('Invalid challenge ID')

	challenge = query('SELECT c.id, c.type, c.meta, u.completed AS completed,
			X(geom) AS latitude, Y(geom) AS longitude FROM `challenges` c
		LEFT JOIN `user_challenges` u
		ON c.id = u.challenge_id
		WHERE c.id=? AND (u.user_id IS NULL OR u.user_id=?);', challenge_id, user['id'])[0]

	halt!('No such challenge!') if !challenge

	data = {
		'success' => 1,
		'id' => challenge['id'],
		'complete' => challenge['completed'] ? true : false,
		'type' => challenge['type']
	}
	data.merge!(JSON.parse(challenge['meta']))

	json(data)

end

post '/api/v1/challenge/complete' do

	user = authorize!
	challenge_id = Integer(params[:challenge_id]) rescue halt!('Invalid challenge ID')

	challenge = query('SELECT c.id, c.type, c.meta, u.completed AS completed, c.points
		FROM `challenges` c
		LEFT JOIN `user_challenges` u
		ON c.id = u.challenge_id
		WHERE c.id=? AND (u.user_id IS NULL OR u.user_id=?);', challenge_id, user['id'])[0]

	halt!('No such challenge!') if !challenge

	points = challenge['completed'] ? 0 : challenge['points']

	query('INSERT INTO `user_challenges`
		(user_id, challenge_id, completed, attempts, score_delta)
		VALUES (?, ?, ?, ?, ?);', user['id'], challenge_id, true, 1, points) if !challenge['completed']
	query('UPDATE `users` SET `score` = `score` + ? WHERE `id`=?;', points, user['id']) if points != 0

	user = authorize!

	json({
		:username => user['username'],
		:id => user['id'],
		:score => user['score'],
		:health => [user['health'], user['health_total']],
		:delta => points
	})

end

post '/api/v1/challenge/fail' do
	halt!('TO-DO', 500)
end


### DEVELOPER UI ###

get '/dev/challenges' do

	challenges = query('SELECT id, type, meta, points, X(geom) AS latitude, Y(geom) AS longitude FROM challenges;')

	erb :'editor.html', :locals => {:challenges => challenges}
end

post '/dev/challenges/riddle' do
	latitude, longitude = Float(params[:latitude]), Float(params[:longitude]) rescue halt!('Bad input')

	points = Integer(params[:points])
	question = params[:question]
	answers = params[:answers].split ';'
	type = 'RIDDLE'

	raise 'Need a question' if question.empty?
	raise 'Need at least one answer' if answers.length == 0

	json = { 'question' => question, 'answers' => answers }.to_json
	query('INSERT INTO `challenges` (type, meta, geom, points) VALUES (?, ?, POINT(?, ?), ?);', 
		type, json, latitude, longitude, points)

	redirect to('/dev/challenges')
end





### HELPER METHODS ###

def json keys
	content_type :json
	keys.to_json
end

def authorize!

	token = 'thisisatesttoken' # request.env['HTTP_AUTHORIZATION'][6..-1]
	res = query('SELECT * FROM `users` WHERE token=?;', token)
	halt!('Bad credentials', 403) if res.length != 1

	return res[0]
end

def halt! error, code = 400
	throw(:halt, [code, json({ :success => 0, :error => error })])
end

def query q, *params
	$connection = Mysql2::Client.new $config['database']['mysql2'].merge({:reconnect => true}) if !$connection
	query = $connection.prepare(q)
	result = query.execute(*params)
	result.to_a
end

def hash! password
	return Digest::SHA2.new(512).hexdigest(password)
end