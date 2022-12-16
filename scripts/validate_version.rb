#!/usr/bin/env ruby

current_tag = ARGV[0].strip

if current_tag.empty?
  puts 'No tag found! Pass the tag as an argument to this script.'
  exit 2
end

root_file = File.read('build.gradle')
version = root_file.match(/version = '(.*)'/)[1]

puts "Current tag: #{current_tag}"
puts "Version: #{version}"

if current_tag != version
  puts 'Tag and version do not match!'
  exit 1
end
