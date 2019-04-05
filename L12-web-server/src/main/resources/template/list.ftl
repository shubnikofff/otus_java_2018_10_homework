<html lang="en">
    <head>
        <title>User list</title>
        <link rel="stylesheet" type="text/css" href="/css/list.css">
    </head>
    <body>
        <h3>User list</h3>
        <table>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Age</th>
                <th>Address</th>
                <th>Phones</th>
            </tr>
            <#list userList as user>
                <tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.age}</td>
                    <td>${user.address}</td>
                    <td>
                        <#list user.phones as phone>
                            <span>${phone}&nbsp;</span>
                        </#list>
                    </td>
                </tr>
            <#else>
                <tr>
                    <td colspan="5" style="text-align: center">No users yet</td>
                </tr>
            </#list>
        </table>
    </body>
</html>
