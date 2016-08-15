<table>
<#if tasks ?? && tasks?size gt 0>
    <#list tasks as temp>
        <tr>
            <td>${temp.task.id!''}</td>
            <td>${temp.task.name!''}</td>
            <td>${temp.username!''}</td>
            <td><a href="/tpl/apply/${temp.task.id}/${temp.task.executionId}">批准</a></td>
            <td><img alt="" src="/tpl/image/${temp.processId}"/></td>
        </tr>
    </#list>
</#if>

</table>

<table>
<#if tasks2 ?? && tasks2?size gt 0>
    <#list tasks2 as temp>
        <tr>
            <td>${temp.task.id!''}</td>
            <td>${temp.task.name!''}</td>
            <td>${temp.username!''}</td>
            <td><a href="/tpl/claim/${temp.task.id}/${temp.task.executionId}">claim</a></td>
            <td><img alt="" src="/tpl/image/${temp.processId}"/></td>
        </tr>
    </#list>
</#if>

</table>



