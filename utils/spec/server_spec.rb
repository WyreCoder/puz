ENV['RACK_ENV'] = 'test'

require './server'

require 'spec_helper'
require 'rspec'
require 'rack/test'

describe 'Server' do
	include Rack::Test::Methods
	def app
		Sinatra::Application
	end

	it "says hello" do
		get '/'
		expect(last_response).to be_ok
	end
end