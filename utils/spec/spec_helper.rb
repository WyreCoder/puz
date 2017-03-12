begin
	require 'simplecov'
	SimpleCov.start
rescue LoadError; end

RSpec.configure do |config|

  config.add_setting :test_port, :default => 12839

  config.expect_with :rspec do |expectations|
    expectations.include_chain_clauses_in_custom_matcher_descriptions = true
  end

  config.mock_with :rspec do |mocks|
    mocks.verify_partial_doubles = true
  end

end