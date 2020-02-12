<%-- 
    Document   : managestock
    Created on : Nov 30, 2019, 9:43:30 PM
    Author     : User
--%>

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
                        <h4><i class="icon-reorder"></i> Manage Stock</h4>                            
                    </div>
                    <div id='message'></div>
                    <div class="widget-body">
                        <table class="table table-striped table-bordered">
                            <thead>
                            <tr>
<!--                                <th class="hidden-phone">S.No.</th>-->
                                <th class="hidden-phone">Product Name</th>
                                <th class="hidden-phone">Quantity</th>
                                
                                <th class="hidden-phone">Action</th>
                            </tr>
                            </thead>
                           
                            <tbody>
                            <c:forEach var="product" items="${stocks}">
                            <tr class="odd gradeX">
                                
                                <td>${stock.id} }</td>
                                <td>${stock.quantity}</td>
                                                               
                                <td>
                                    <a href="#" class="btn btn-success"><i class="icon-user"></i></a>
                                    <a href="updateproduct?ref=${stock.id}" class="btn btn-primary"><i class="icon-pencil"></i></a>
                                    <a href="#deleteconfirm" data-toggle="modal" class="btn btn-danger delete-confirm" data="${product.id}"><i class="icon-trash"></i></a>
                                </td>                                
                            </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                        <!-- Modal -->                        
                        <div id="deleteconfirm" style="background-color: #F0F0F0;" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel3" aria-hidden="true">                        
                            <div class="modal-body">
                                <p style="font-size: 25px; color: red;"><i class="icon-remove-sign"> Are you sure you want to delete this Product stock??????</i></p>
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
      var  user = val;
        $.ajax({
          url: "deleteproduct",
          type: "GET",
          data: {ref:product},
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

