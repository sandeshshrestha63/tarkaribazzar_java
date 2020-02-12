<%-- 
    Document   : manageenquiry
    Created on : Nov 30, 2019, 9:21:54 PM
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
            <!-- BEGIN ADVANCED TABLE widget-->
            <div class="row-fluid">
                <div class="span12">
                <!-- BEGIN EXAMPLE TABLE widget-->
                <div class="widget red">
                    <div class="widget-title">
                        <h4><i class="icon-reorder"></i> Manage Product</h4>                            
                    </div>
                    <div id='message'></div>
                    <div class="widget-body">
                        <table class="table table-striped table-bordered">
                            <thead>
                            <tr>
<!--                                <th class="hidden-phone">S.No.</th>-->
                                <th class="hidden-phone">Name</th>
                                <th class="hidden-phone">Email</th>
                                <th class="hidden-phone">Title</th>
                                <th class="hidden-phone">Message</th>
                            </tr>
                            </thead>
                           
                            <tbody>
                            <c:forEach var="product" items="${enquiries}">
                            <tr class="odd gradeX">
                                
                                <td>${enquiry.name} }</td>
                                <td>${enquiry.email}</td>
                                <td>${enquiry.title}</td>
                                <td>${enquiry.message}</td>
                                                               
                                <td>
                                    <a href="#" class="btn btn-success"><i class="icon-user"></i></a>
                                    <a href="#deleteconfirm" data-toggle="modal" class="btn btn-danger delete-confirm" data="${enquiry.id}"><i class="icon-trash"></i></a>
                                </td>                                
                            </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                        <!-- Modal -->                        
                        <div id="deleteconfirm" style="background-color: #F0F0F0;" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel3" aria-hidden="true">                        
                            <div class="modal-body">
                                <p style="font-size: 25px; color: red;"><i class="icon-remove-sign"> Are you sure you want to delete this Enquiry??????</i></p>
                            </div>
                            <div class="modal-footer">                                    
                                <button data-dismiss="modal" id="yes" class="btn btn-danger">Yes</button>
                                <button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">No</button>
                            </div>
                        </div>
                            
                    </div>
                </div>

                </div>
            </div>

            <!-- END ADVANCED TABLE widget-->
         </div>
         <!-- END PAGE CONTAINER-->
      </div>
      <!-- END PAGE -->  
   </div>
   <!-- END CONTAINER -->

   <!-- BEGIN FOOTER -->
   <div id="footer">
       &copy; Karuna Hospital Pvt. Ltd.
   </div>
   <!-- END FOOTER -->

   <!-- BEGIN JAVASCRIPTS -->
   <!-- Load javascripts at bottom, this will reduce page load time -->
   <script src="assets/admin/js/jquery-1.8.3.min.js"></script>
   <script src="assets/admin/js/jquery.nicescroll.js" type="text/javascript"></script>
   <script src="assets/admin/plugins/bootstrap/js/bootstrap.min.js"></script>
   <script src="assets/admin/js/jquery.blockui.js"></script>
   <!-- ie8 fixes -->
   <!--[if lt IE 9]>
   <script src="js/excanvas.js"></script>
   <script src="js/respond.js"></script>
   <![endif]-->
   <script type="text/javascript" src="assets/admin/plugins/uniform/jquery.uniform.min.js"></script>
   <script type="text/javascript" src="assets/admin/plugins/data-tables/jquery.dataTables.js"></script>
   <script type="text/javascript" src="assets/admin/plugins/data-tables/DT_bootstrap.js"></script>
   <script src="assets/admin/js/jquery.scrollTo.min.js"></script>


   <!--common script for all pages-->
   <script src="assets/admin/js/common-scripts.js"></script>
   <!-- END JAVASCRIPTS -->   
</body>
<!-- END BODY -->
</html>
<script type="text/javascript">
  $(document).ready(function(){
     var val='';
    $('.delete-confirm').click(function(){
     val = $(this).attr("data");
     return val;
  });

    $('#yes').click(function(){
      var  enquiry = val;
        $.ajax({
          url: "deleteenquiry",
          type: "GET",
          data: {ref:enquiry},
          success: function(){
            $('#message').addClass("alert alert-block alert-success fade in");            
            $('#message').append('<h4 class="alert-heading" style="font-size:18px;">Delete Success!!!</h4>');
            $('#message').fadeOut(3000);
            window.setTimeout(function(){location.reload()},3000)
          }
        });
    });
  });
</script>

