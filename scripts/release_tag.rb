#!/usr/bin/env ruby

root_file = File.read('build.gradle')
version = root_file.match(/version = '(.*)'/)[1]

puts "Prepareing to release version #{version}…"

puts 'Creating tag…'
`git tag -d #{version}`
`git tag #{version}`

puts 'Pushing tag…'
`git push origin #{version} --force`

puts 'Done'
