package com.hat.test.acceptance


import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = [TestConfig.class])
class BaseSpec extends Specification {


}
