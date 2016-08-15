

<table>
<#if tasks ?? && tasks?size gt 0>
        <#list tasks as temp>
        <tr>
        <td>${temp.task.id!''}</td>
        <td>${temp.task.name!''}</td>
        <td>${temp.username!''}</td>
        <td>
            <form action="/tpl/haryassign">
                <input name="name" value="请输入名字" type="text" />
                <input name="taskId" value="${temp.task.id}"  type="hidden" />
                <input type="submit" value="提交"/>
            </form>
        </td>
        <td><img alt="" src="/tpl/image/${temp.processId}"/></td>
        </tr>
        </#list>
</#if>

</table>