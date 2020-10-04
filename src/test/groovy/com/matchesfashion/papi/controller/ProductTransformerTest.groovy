package com.matchesfashion.papi.controller

import com.matchesfashion.papi.DomainTestData
import org.junit.Test

import static com.matchesfashion.papi.TestUtils.marshal

class ProductTransformerTest {

    @Test
    void 'test transform'() {
        def domain = DomainTestData.product()
        def api = ProductTransformer.toApi(domain)

        //because the domain and API objects currently shadow each other, we can do a super simple test by turning
        //the source and target objects to JSON
        assert domain != api
        assert marshal(domain) == marshal(api)
    }
}
