<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="grid-container">
    <c:forEach var="t" items="${availableTables}">
        <button type="button" 
                class="table-btn table-item space-${t.spaceId} ${t.isBooked ? 'occupied' : 'available'}" 
                ${t.isBooked ? 'disabled' : ''}
                data-id="${t.tableId}"
                data-space="${t.spaceId}">
            ${t.tableName}
            <c:if test="${t.isBooked}"><br><small>(Hết chỗ)</small></c:if>
        </button>
    </c:forEach>
</div>