<%-- 
    Document   : pagination
    Created on : May 9, 2022, 1:58:58 AM
    Author     : wellinton
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<ul class="pagination">
    <li class="page-item button-prev">
        <a class="page-link">Voltar</a>
    </li>
    
    <li class="page-item active">
        <a class="page-link">
            <c:choose>
                <c:when test="${param.page==null}">
                    1
                </c:when>    
                <c:otherwise>
                    ${param.page}
                </c:otherwise>
            </c:choose>
        </a>
    </li>
    
    <li class="page-item button-next">
        <a class="page-link" >
            Próxima
        </a>
    </li>
</ul>



