<%-- 
    Document   : updatestock
    Created on : Nov 30, 2019, 9:41:28 PM
    Author     : User
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" />
<jsp:include page="sidebar.jsp" />

<!-- BEGIN PAGE -->      
      <div id="main-content">
         <!-- BEGIN PAGE CONTAINER-->
         <div class="container-fluid">
            <!-- BEGIN PAGE CONTENT-->
             <div class="row-fluid">
                 <div class="span12">
                     <!-- BEGIN VALIDATION STATES-->
                     <div class="widget green">
                         <div class="widget-title">
                             <h4><i class=" icon-user"></i> Update Stock</h4>
                         </div>
                         <div class="widget-body form">
                             <!-- BEGIN FORM-->
                             <form id="validate_form" action="" method="POST" class="cmxform form-horizontal" enctype="multipart/form-data"> 
                              <div class="control-group ">
                                  <label for="product_id" class="control-label">Product Name</label>
                                  <div class="controls ">
                                      <select name="product_id">
                                          <option value="#"></option>
                                      </select> 
                                  </div>
                              </div>
                              <div class="control-group ">
                                  <label for="quantity" class="control-label">Quantity</label>
                                  <div class="controls ">
                                      
                                    <input type="text" style="width: 150px;" name="quantity" id="quantity" placeholder="Available quantity" required data-parsley-pattern="^[0-9]+$" data-parsley-trigger="keyup" class="span6" />
                                  </div>
                              </div>                                        
                                <div class="form-actions">
                                    <button class="btn btn-success" name="btnsubmit" type="submit">Save</button>
                                    <button class="btn" type="button">Cancel</button>
                                </div>
                            </form>
                            <!-- END FORM-->                             
                         </div>
                     </div>
                     <!-- END VALIDATION STATES-->
                 </div>
             </div>           

            <!-- END PAGE CONTENT-->

         </div>
         <!-- END PAGE CONTAINER-->
      </div>
      <!-- END PAGE -->


