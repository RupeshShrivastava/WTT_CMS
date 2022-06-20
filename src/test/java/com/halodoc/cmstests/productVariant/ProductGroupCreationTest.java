package com.halodoc.cmstests.productVariant;


import com.halodoc.cmstests.BaseCMSTest;
import com.halodoc.cmstests.CMSHelper;
import com.halodoc.cmstests.apiDefinitions.ProductVarianceApi;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

import static com.halodoc.cmstests.Constants.*;
import static com.halodoc.cmstests.ErrorCodesAndMessages.GROUP_ID_NOT_FOUND_EXCEPTION;

public class ProductGroupCreationTest extends BaseCMSTest {

    private static Logger logger = Logger.getLogger(ProductGroupCreationTest.class);

    public int groupId;
    public String groupStatus;
    public String groupName;
    public int expectedGroupId;
    public String externalId;
    public String newGrpName;
    public String actualGroupName;
    public ProductVarianceApi productVarianceApi;
    private HashMap<String, String> headers = getAppTokenHeaders(X_APP_TOKEN_CMS);
    private CMSHelper cmsHelper;

    public ProductGroupCreationTest(){
        productVarianceApi = new ProductVarianceApi();
        cmsHelper = new CMSHelper();
        groupName = cmsHelper.randomString();
        newGrpName = "NewGroupName";

    }

    @Test(description = "Create product group", groups = {"sanity", "regression"}, priority = 0)
    public void testCreateProductGroup() throws Exception {
        logger.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response createResponse  = productVarianceApi.createProductGroup(headers,groupName);

        Assert.assertEquals(createResponse.getStatusCode(), HttpStatus.SC_CREATED);
        groupId = createResponse.path("id");
        externalId = createResponse.path("external_id");
        Response getGroupResponse = productVarianceApi.getProductGroupRequest(headers,groupId+"");
        Assert.assertEquals(getGroupResponse.getStatusCode(), HttpStatus.SC_OK);
        groupStatus = getGroupResponse.path("status");
        expectedGroupId = getGroupResponse.path("id");
        Assert.assertEquals(groupId,expectedGroupId,"group id is not matching");
        Assert.assertEquals(groupName,getGroupResponse.path("name"),"group id is not matching");
        Assert.assertEquals(groupStatus,"active","status is not matching");

    }

    @Test(description = "Update product group status to inactive", groups = {"sanity", "regression"}, priority = 1)
    public void testUpdateProductGroupInactive() throws Exception {
        logger.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response updateResponse  = productVarianceApi.updateProductGroup(headers,groupId+"",groupName,"inactive");
        Assert.assertEquals(updateResponse.getStatusCode(), HttpStatus.SC_OK);

        Response getGroupResponse = productVarianceApi.getProductGroupRequest(headers,groupId+"");
        Assert.assertEquals(getGroupResponse.getStatusCode(), HttpStatus.SC_OK);
        groupStatus = getGroupResponse.path("status");
        Assert.assertEquals(groupStatus,"inactive","status is not matching");

        //update group status to active
        productVarianceApi.updateProductGroup(headers,groupId+"",groupName,"active");
    }

    @Test(description = "Update product group name with new group name", groups = {"sanity", "regression"})
    public void testUpdateProductGroupNewName() throws Exception {
        logger.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        //new group name
        Response updateResponse  = productVarianceApi.updateProductGroup(headers,groupId+"",newGrpName,"active");
        Assert.assertEquals(updateResponse.getStatusCode(), HttpStatus.SC_OK);

        Response getGroupResponse =productVarianceApi.getProductGroupRequest(headers,groupId+"");
        Assert.assertEquals(getGroupResponse.getStatusCode(), HttpStatus.SC_OK);
        actualGroupName = getGroupResponse.path("name");
        Assert.assertEquals(actualGroupName,newGrpName, "group name is not changed");
        //reverse the group name to older one
        productVarianceApi.updateProductGroup(headers,groupId+"",newGrpName,"active");

    }

    @Test(description = "Update group name to already existing group name", groups = {"sanity", "regression"})
    public void testUpdateGroupNameAlreadyExistingGrpName() throws Exception {
        logger.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());

        Response updateResponse  = productVarianceApi.updateProductGroup(headers,groupId+"","FIFTHGROUP","active");
        Assert.assertEquals(updateResponse.getStatusCode(), HttpStatus.SC_BAD_REQUEST);

    }

        @Test(description = "Update product group status to inactive for non existing group id", groups = {"sanity", "regression"})
    public void testUpdateProductGroupInactiveNonExistingGroup() throws Exception {
        logger.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
            //incorrect status
            Response updateResponse  = productVarianceApi.updateProductGroup(headers,NON_EXISTING_GROUP_ID ,"1289@@#@#","inactive");
            Assert.assertEquals(updateResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);

    }

        @Test(description = "Update product group status to inactive with incorrect/null/empty status", groups = {"sanity", "regression"})
    public void testUpdateProductGroupInactiveIncorrectStatus() throws Exception {
        logger.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
            //incorrect status
            Response updateResponse  = productVarianceApi.updateProductGroup(headers,groupId+"",groupName,"#$@@@");
            Assert.assertEquals(updateResponse.getStatusCode(), HttpStatus.SC_BAD_REQUEST);

             //empty status
             updateResponse  = productVarianceApi.updateProductGroup(headers,groupId+"",groupName,"");
            Assert.assertEquals(updateResponse.getStatusCode(), HttpStatus.SC_BAD_REQUEST);

    }

    @Test(description = "Create product group with empty name", groups = {"sanity", "regression"})
    public void testCreateProductGroupWithEmptyName() throws Exception {
        logger.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response createResponse  = productVarianceApi.createProductGroup(headers,"");

        Assert.assertEquals(createResponse.getStatusCode(), HttpStatus.SC_UNPROCESSABLE_ENTITY);

    }

    @Test(description = "Get product group details with existing Id", groups = {"sanity", "regression"})
    public void testGetProductGroupDetailsWithExistingId() throws Exception {
        logger.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response getGroupResponse =productVarianceApi.getProductGroupRequest(headers,EXISTING_GROUP_ID+"");
        Assert.assertEquals(getGroupResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(description = "Get product group details with non existing Id", groups = {"sanity", "regression"})
    public void testGetProductGroupDetailsWithNonExistingId() throws Exception {
        logger.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response getGroupResponse =productVarianceApi.getProductGroupRequest(headers,NON_EXISTING_GROUP_ID);
        Assert.assertEquals(getGroupResponse.getBody().path("message"), GROUP_ID_NOT_FOUND_EXCEPTION.errMsg());

    }

    @Test(description = "Get search product group details ", groups = {"sanity", "regression"})
    public void testSearchProductGroupDetails() throws Exception {
        logger.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response getGroupResponse = productVarianceApi.searchProductGroup(headers,groupName);
        Assert.assertEquals(getGroupResponse.getStatusCode(), HttpStatus.SC_OK);
        int total  = getGroupResponse.jsonPath().get("total_count");
        Assert.assertEquals(total,1,"search count is not matching");
        List listProduct = getGroupResponse.jsonPath().get("result");
        HashMap map = (HashMap) listProduct.get(0);
        Assert.assertEquals(map.get("name"),groupName,"search group name is not matching");
    }

    @Test(description = "Search product group with non existing group name", groups = {"sanity", "regression"})
    public void testSearchProductGroupNonExistingGroupName() throws Exception {
        logger.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response getGroupResponse = productVarianceApi.searchProductGroup(headers,"#@@#");
        Assert.assertEquals(getGroupResponse.getStatusCode(), HttpStatus.SC_OK);
        int total  = getGroupResponse.jsonPath().get("total_count");
        Assert.assertEquals(total,0,"search count is not matching");
        List listProduct = getGroupResponse.jsonPath().get("result");
        Assert.assertTrue(listProduct.isEmpty(),"group found");
    }

    @Test(description = "Search product group with empty group name", groups = {"sanity", "regression"})
    public void testSearchProductGroupNonEmptyGroupName() throws Exception {
        logger.info("Running Test : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Response getGroupResponse = productVarianceApi.searchProductGroup(headers,"");
        Assert.assertEquals(getGroupResponse.getStatusCode(), HttpStatus.SC_OK);
        int total  = getGroupResponse.jsonPath().get("total_count");
        Assert.assertNotEquals(total,0,"search count is not matching");
        List listProduct = getGroupResponse.jsonPath().get("result");
        Assert.assertTrue(!listProduct.isEmpty(),"group found");
    }


}
