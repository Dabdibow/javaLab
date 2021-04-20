<#ftl encoding='UTF-8'>
<!doctype html>
<html lang="en">
<#import "spring.ftl" as spring/>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sign in</title>
    <style>
        .error {
            color: red;
        }
    </style>
</head>

<body>
<h2><a href="?lang=ru">РУ</a> </h2>
<h2><a href="?lang=en">EN</a> </h2>
<h2><a href="/signUp"><@spring.message 'sign_up_page.registration.title'/></a></h2>
<h1><@spring.message 'sign_in_page.title'/></h1>
<@spring.bind "userForm"/>
<#if namesErrorMessage??>
    <p style=".error">${namesErrorMessage}</p>
</#if>
<form action="/signIn" method="post">

    <label>Email</label>
    <@spring.formInput "userForm.email"/>
    <@spring.showErrors "<br>" "error"/>

    <label><@spring.message 'sign_in_page.password'/></label>
    <@spring.formPasswordInput "userForm.password"/>
    <@spring.showErrors "<br>" "error"/>

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <label>
        <input type="checkbox" name="remember-me">Запомни меня
    </label>
    <input type="submit" value=<@spring.message 'sign_in_page.title'/>>

</form>
</body>
</html>