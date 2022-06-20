package com.halodoc.cmstests.locations;

import static com.halodoc.cmstests.Constants.CMS_URL_V2;
import static com.halodoc.cmstests.Constants.MERCHANT_LOCATIONS_V2;
import static com.halodoc.cmstests.Constants.X_APP_TOKEN;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import com.halodoc.cmstests.BaseCMSTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by nageshkumar
 * since  26/03/18.
 */
@Slf4j
public class MerchantLocationsV2 extends BaseCMSTest {
    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = CMS_URL_V2;
    }

    @Test
    public void getAllMerchantLocations() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response response=given().

                       contentType("application/json").
                       header("X-APP-TOKEN", X_APP_TOKEN)
                        .param("per_page","90").
                       expect().
                       log().all().
                       statusCode(200).
                       body(containsString("id")).
                       body(containsString("name")).
                       body(containsString("address_line")).
                       body(containsString("business_hours")).
                       when().
                       get(MERCHANT_LOCATIONS_V2);
    List<String> merchant_external_id=new ArrayList<>();
    merchant_external_id=response.path("result.external_id[0]");
   int total= merchant_external_id.size();
        List<String> merchant_temp=new ArrayList<>();
merchant_temp=response.path("result.merchant_id["+total+"]");

        HashMap<String ,String > merchant_details=new HashMap<>();

//        for(int index=)
    }

    @Test
    public void checkPaginationFilter() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        given().

                       contentType("application/json").
                       header("X-APP-TOKEN", X_APP_TOKEN).
                       queryParam("page_no", "1").
                       queryParam("per_page", "8").
                       expect().
                       log().all().
                       statusCode(200).
                       body(containsString("id")).
                       body(containsString("name")).
                       body(containsString("address_line")).
                       body(containsString("business_hours")).
                       body("result.size()", equalTo(8)).
                       body("next_page",equalTo(true)).
                       when().
                       get(MERCHANT_LOCATIONS_V2);

    }

    @Test
    public void checkStatusInactiveFilter() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        given().

                       contentType("application/json").
                       header("X-APP-TOKEN", X_APP_TOKEN).
                       queryParam("page_no", "1").
                       queryParam("per_page", "1").
                       queryParam("statuses", "inactive").
                       expect().
                       log().all().
                       statusCode(200).
                       body(containsString("id")).
                       body(containsString("name")).
                       body(containsString("address_line")).
                       body(containsString("business_hours")).
                       body("result[0].status", equalTo("inactive")).
                       when().
                       get(MERCHANT_LOCATIONS_V2);

    }

    @Test
    public void checkStatusActiveFilter() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        given().

                       contentType("application/json").
                       header("X-APP-TOKEN", X_APP_TOKEN).
                       queryParam("page_no", "1").
                       queryParam("per_page", "1").
                       queryParam("statuses", "active").
                       expect().
                       log().all().
                       statusCode(200).
                       body(containsString("id")).
                       body(containsString("name")).
                       body(containsString("address_line")).
                       body(containsString("business_hours")).
                       body("result[0].status", equalTo("active")).
                       when().
                       get(MERCHANT_LOCATIONS_V2);

    }

    @Test
    public void checkNameFilter() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        given().

                       contentType("application/json").
                       header("X-APP-TOKEN", X_APP_TOKEN).
                       queryParam("page_no", "1").
                       queryParam("per_page", "1").
                       queryParam("name", "plaza").
                       expect().
                       log().all().
                       statusCode(200).
                       body(containsString("id")).
                       body(containsString("name")).
                       body(containsString("address_line")).
                       body(containsString("business_hours")).
                       body("result[0].name", containsString("PLAZA")).
                       when().
                       get(MERCHANT_LOCATIONS_V2);

    }

    @Test
    public void checkMerchantIdsFilter() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        given().

                       contentType("application/json").
                       header("X-APP-TOKEN", X_APP_TOKEN).
                       queryParam("page_no", "1").
                       queryParam("per_page", "1").
                       queryParam("merchant_ids", "8d429c7d-2be1-46cc-88bd-239d8988b94b").
                       expect().
                       log().all().
                       statusCode(200).
                       body(containsString("id")).
                       body(containsString("name")).
                       body(containsString("address_line")).
                       body(containsString("business_hours")).
                       body("result[0].merchant_id", equalTo("8d429c7d-2be1-46cc-88bd-239d8988b94b")).
                       when().
                       get(MERCHANT_LOCATIONS_V2);
    }

    @Test
    public void checkMultipleMerchantIdsFilter() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        given().

                       contentType("application/json").
                       header("X-APP-TOKEN", X_APP_TOKEN).
                       queryParam("merchant_ids", "8d429c7d-2be1-46cc-88bd-239d8988b94b,643ca290-1ea5-423f-bee7-9ebc4f18c857").
                       expect().
                       log().all().
                       statusCode(200).
                       body(containsString("id")).
                       body(containsString("name")).
                       body(containsString("address_line")).
                       body(containsString("business_hours")).
                       body(containsString("8d429c7d-2be1-46cc-88bd-239d8988b94b")).
                       body(containsString("643ca290-1ea5-423f-bee7-9ebc4f18c857")).
                       when().
                       get(MERCHANT_LOCATIONS_V2);
    }

    @Test
    public void checkMerchantLocationIdsFilter() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        given().

                       contentType("application/json").
                       header("X-APP-TOKEN", X_APP_TOKEN).
                       queryParam("page_no", "1").
                       queryParam("per_page", "1").
                       queryParam("merchant_location_ids", "93770f73-51e7-418c-84f5-2e2328befdca").
                       expect().
                       log().all().
                       statusCode(200).
                       body(containsString("id")).
                       body(containsString("name")).
                       body(containsString("address_line")).
                       body(containsString("business_hours")).
                       body("result[0].external_id", equalTo("93770f73-51e7-418c-84f5-2e2328befdca")).
                       when().
                       get(MERCHANT_LOCATIONS_V2);
    }

    @Test
    public void checkMultipleMerchantLocationIdsFilter() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        given().

                       contentType("application/json").
                       header("X-APP-TOKEN", X_APP_TOKEN).
                       queryParam("merchant_location_ids", "93770f73-51e7-418c-84f5-2e2328befdca,732f3973-7bd0-4e14-aab0-dbba450bf8d5").
                       expect().
                       log().all().
                       statusCode(200).
                       body(containsString("id")).
                       body(containsString("name")).
                       body(containsString("address_line")).
                       body(containsString("business_hours")).
                       body(containsString("93770f73-51e7-418c-84f5-2e2328befdca")).
                       body(containsString("732f3973-7bd0-4e14-aab0-dbba450bf8d5")).
                       when().
                       get(MERCHANT_LOCATIONS_V2);
    }

    @Test
    public void checkMerchantAndMerchantLocationIdsFilter() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        given().

                       contentType("application/json").
                       header("X-APP-TOKEN", X_APP_TOKEN).
                       queryParam("merchant_ids", "8d429c7d-2be1-46cc-88bd-239d8988b94b").
                       queryParam("merchant_location_ids", "93770f73-51e7-418c-84f5-2e2328befdca").
                       expect().
                       log().all().
                       statusCode(200).
                       body(containsString("id")).
                       body(containsString("name")).
                       body(containsString("address_line")).
                       body(containsString("business_hours")).
                       body(containsString("8d429c7d-2be1-46cc-88bd-239d8988b94b")).
                       body(containsString("93770f73-51e7-418c-84f5-2e2328befdca")).
                       when().
                       get(MERCHANT_LOCATIONS_V2);
    }

    @Test
    public void noXappToken() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        given().

                       contentType("application/json").
                       expect().
                       log().all().
                       statusCode(400).
                       body("code",equalTo("1102")).
                       body("status_code",equalTo(400)).
                       body("message",equalTo("App token is needed for this operation")).
                       when().
                       get(MERCHANT_LOCATIONS_V2);

    }

    @Test
    public void invalidXappToken() {
        log.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        given().

                       contentType("application/json").
                       header("X-APP-TOKEN", "1234").
                       expect().
                       log().all().
                       statusCode(401).
                       body("code",equalTo("1103")).
                       body("status_code",equalTo(401)).
                       body("message",equalTo("App token is not authorised to perform this operation")).
                       when().
                       get(MERCHANT_LOCATIONS_V2);

    }

}
