<%-- 
    Document   : header
    Created on : May 3, 2022, 6:18:33 PM
    Author     : wellinton
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html>
  <head>
    <title>Price Integration System</title>
    <%@include file="/include/head.jsp"%>  
    <link rel="stylesheet" href="./styles/header.css"/>
  </head>
  
  <body id="header">
        <div class="container-fluid col-12 d-flex bg-dark">
            <div class="col-3 d-flex align-items-center justify-content-center">
                Price Integration System
            </div>
            
            <div class="col-6 d-flex align-items-center justify-content-center">
                <form class="form w-100 d-flex align-items-center justify-content-center " action="${pageContext.servletContext.contextPath}/search" method="GET">
                    <div class="form-group w-75">
                        <label class="control-label" for="search-bar"></label>
                        <input id="search-bar" class="form-control" type="text" name="searchBar" placeholder="Qual modelo deseja ver?" autofocus/>
                        <p class="help-block"></p>
                    </div>
                    <div class="text-center">
                        <button class="btn btn-lg" type="submit"><i class="fa fa-search"></i></button>
                    </div>
                </form>
            </div>
        </div>
  </body>
</html>