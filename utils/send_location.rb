#!/usr/bin/env ruby
require 'socket'

class Location
	def get_location
		my_location = `./whereami`
		decode my_location
	end
	private
	def decode string
		lat = string.match(/Latitude: ([\-0-9\.]+)/)[1].to_f
		lng = string.match(/Longitude: ([\-0-9\.]+)/)[1].to_f
		[lat, lng]
	end
end

class Debugger
	attr_reader :socket
	def initialize ip, port, token
		@socket = TCPSocket.new ip, port
		@socket << "auth #{token}\n"
		sleep 0.1
		puts @socket.recv(4096)
	end
end

def send_location socket, lat, long
	socket.socket << "geo fix #{lat} #{long}\n"
	sleep 0.1
	puts socket.socket.recv(4096)
end

location = Location.new
socket = Debugger.new "127.0.0.1", 5554, File.read("#{Dir.home}/.emulator_console_auth_token")

loop do 
	ln = location.get_location
	puts "Current location: #{ln.join ","}"
	send_location socket, ln[1], ln[0]
	sleep 5
end
