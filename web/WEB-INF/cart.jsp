<%-- 
    Document   : cart
    Created on : Dec 1, 2019, 11:30:04 AM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title>cart page</title>
    <link rel="stylesheet" href="aassets/css/cart.css">
     <link rel="stylesheet" href="aassets/css/index.css">
  </head>
  <body>
      
      
     

<div class="container-wrapper">

  <div class="cart-wrapper">
       
    <!-- product 01 -->
    <div class="product-wrapper">
            
    
<c:forEach var="carts" items="${orders}">
   
      <div class="product-container">
        <div class="product-image">
          <img src="aassets/images/tshirt.jpeg" alt="cart-image">
        </div>
        <div class="product-detail">
            <h3> ${orders.addedtocartitem.productid.name}</h3>
          <p>${orders.addedtocartitem.productid.description}</p>
          
        </div>
      </div>
      <div class="product-response">
        <div class="product-quantity">
          <div class="left-btn btn">-</div>
          <input type="text" name="quantity" id="quantity" class="quantity" value="1">
          <div class="right-btn btn">+</div>
        </div>
        <div class="price">
          <span> ${orders.addedtocartitem.productid.price}</span>
        </div>
      </div>
      </c:forEach>
    </div>
      
        
   

  <!-- right side checkout part -->
  <div class="checkout-wrapper">
    <div class="checkout-header">
      Total
    </div>
<div class="order-detail">
  <p>Total Quanity: <span>2</span></p>
  <p>Total Price: <span> Rs 200</span></p>
</div>
<div class="checkout-btn-wrapper">
  <button type="button" name="button">Checkout></button>
</div>
  </div>
</div>

  </body>
</html>