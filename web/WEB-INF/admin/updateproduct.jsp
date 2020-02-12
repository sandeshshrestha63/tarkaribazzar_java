<%-- 
    Document   : updateproduct
    Created on : Nov 30, 2019, 9:07:35 PM
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
                             <h4><i class=" icon-user"></i> Update Product</h4>
                         </div>
                         <div class="widget-body form">
                             <!-- BEGIN FORM-->
                             <form id="validate_form" action="updateproduct" method="POST" class="cmxform form-horizontal" enctype="multipart/form-data"> 
                              <div class="control-group ">
                                  <label class="control-label">Image</label>
                                  <div class="controls">
                                    <input class="span6" type="file" name="file" data-parsley-fileextension='jpg.png' data-parsley-trigger="keyup" onchange="previewFile()" required  />
                                  </div>
                              </div>
                              <div class="form-group">
                                  <label class="control-label">View Image</label>
                              </div> 
                              <div class="form-group">
                                  <div class="controls">
                                      <img class="img-thumbnail" src="assets/admin/images/profile-pic.jpg" alt="img" >
                                  </div>
                              </div> 
                              <br>
                              <div class="control-group ">
                                  <label for="productname" class="control-label">Product Name</label>
                                  <div class="controls ">
                                    <input type="text" name="name" id="product" placeholder="Product Name" required data-parsley-pattern="^[a-zA-Z]+$" data-parsley-trigger="keyup" class="span6" />
                                  </div>
                              </div>
                              <div class="control-group ">
                                  <label for="price" class="control-label">Price</label>
                                  <div class="controls ">
                                    <input type="text" name="price" id="price" placeholder="price" required data-parsley-pattern="^[0-9]+$" data-parsley-trigger="keyup" class="span6" />
                                  </div>
                              </div>
                                                        
                             
                              <div class="control-group ">
                                    <label for="description" class="control-label">Description</label>
                                    <div class="controls">
                                        <textarea rows="10" cols="20" name="description" required data-parsley-pattern="^[a-zA-Z]+$" ata-parsley-trigger="keyup" class="span6"></textarea>
                                    </div>
                                </div>
                               
                                
                              
                              
                            
<!--                                <div class="control-group">
                                  <label class="control-label">Status</label>
                                  <div class="controls">
                                      <select class="span6 " name="status" data-placeholder="Choose a Category" >
                                          <option value="active" selected>Active</option>
                                          <option value="inactive">Inactive</option>
                                      </select>
                                  </div>
                                </div>-->
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
