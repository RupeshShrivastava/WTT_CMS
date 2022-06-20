package com.halodoc.cmstests;

import com.jayway.restassured.response.ResponseBody;
import org.testng.asserts.SoftAssert;

import static com.halodoc.cmstests.Constants.IMAGES_TYPE_IMAGE_URL;
import static com.halodoc.cmstests.Constants.IMAGES_TYPE_THUMB_NAIL_URL;

public class ResponseValidations {
    public void validateExceptionCodeAndMessage(String errCOde, String errMsg, ErrorCodesAndMessages errorCodesAndMessages){
        SoftAssert softAssert = new SoftAssert() ;
        softAssert.assertEquals(errCOde, errorCodesAndMessages.errCode(), "Failed at validating error code.");
        softAssert.assertEquals(errMsg, errorCodesAndMessages.errMsg(), "Failed at validating error message.");
        softAssert.assertAll();
    }

    public void verifyMulipleImages(ResponseBody responseBody){
        SoftAssert softAssert = new SoftAssert() ;
        for(int i=0; i<Integer.parseInt(responseBody.path("images.size()").toString()); i++){
            softAssert.assertTrue(responseBody.path("images["+i+"].type").toString().equals(IMAGES_TYPE_THUMB_NAIL_URL) || responseBody.path("images["+i+"].type").toString().equals(IMAGES_TYPE_IMAGE_URL), "Failed at the validation of 'images type'");
            softAssert.assertNotNull(responseBody.path("images["+i+"].extension"), "Failed at the validation of 'images extension'");
            softAssert.assertNotNull(responseBody.path("images["+i+"].url"), "Failed at the validation of 'images url'");
        }
        softAssert.assertAll();
    }

    public void verifyMulipleImages(io.restassured.response.ResponseBody responseBody){
        SoftAssert softAssert = new SoftAssert() ;
        for(int i=0; i<Integer.parseInt(responseBody.path("images.size()").toString()); i++){
            softAssert.assertTrue(responseBody.path("images["+i+"].type").toString().equals(IMAGES_TYPE_THUMB_NAIL_URL) || responseBody.path("images["+i+"].type").toString().equals(IMAGES_TYPE_IMAGE_URL), "Failed at the validation of 'images type'");
            softAssert.assertNotNull(responseBody.path("images["+i+"].extension"), "Failed at the validation of 'images extension'");
            softAssert.assertNotNull(responseBody.path("images["+i+"].url"), "Failed at the validation of 'images url'");
        }
        softAssert.assertAll();
    }
}
