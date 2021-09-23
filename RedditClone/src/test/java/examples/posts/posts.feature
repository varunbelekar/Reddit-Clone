Feature: Test Posts API in Reddit

  Background:
    * url 'http://localhost:8080'

  Scenario: get all posts
    Given path 'api/posts'
    When method get
    Then status 200
    
    * def first = response[0]
    
    Given path 'api/posts', first.blabal
    When method get
    Then status 200
    
  Scenario: get post by postId
    Given path 'api/posts/6'
    When method get
    Then status 200
    And match response.id == 6
    And match response.voteCount == 0
    And match response.postName == "How to stay updated with the latest technologies"

    Given path 'api/posts/10'
    When method get
    Then status 500
  