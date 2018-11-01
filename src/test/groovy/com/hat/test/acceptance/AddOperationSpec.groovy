package com.hat.test.acceptance

import groovy.json.JsonSlurper
import io.restassured.RestAssured
import io.restassured.response.Response
import org.apache.http.HttpHeaders

class AddOperationSpec extends BaseSpec {

    def "should add new income operation"() {

        given: "add operation details"
        def type = "income"
        def date = "2018-10-19"
        def description = "Some test description"
        def amount = 12.59

        when: "request is sent"
        Response postResponse = RestAssured.given()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body("{" +
                "\"type\":\"${type}\"," +
                "\"date\":\"${date}\"," +
                "\"description\":\"${description}\"," +
                "\"amount\":${amount}" +
                "}")
                .post("/operations")

        then: "response is successful"
        postResponse.statusCode() == 201

        and: "response contains operation details"
        def postResponseJson = new JsonSlurper().parseText(postResponse.body as String) as Map
        verifyAll {
            postResponseJson.type == type
            postResponseJson.amount == amount
            postResponseJson.date == "2018-06-17"
            postResponseJson.description == description
        }

        and: "response contains URL to operation"
        def linkList = postResponseJson.links as List
        def selfLink = ""
        linkList.each({
            def currentLink = it as Map
            if (currentLink.rel == "self") {
                selfLink = currentLink.href
            }
        })
        selfLink.contains("operations")

        and: "URL from response points to actual operation"
        Response getResponse = RestAssured.given()
                .get(selfLink)
        def getResponseJson = new JsonSlurper().parseText(getResponse.body() as String) as Map
        verifyAll {
            getResponseJson.type == type
            getResponseJson.amount == amount
            getResponseJson.date == "2018-06-17"
            getResponseJson.description == description
        }
    }

}
