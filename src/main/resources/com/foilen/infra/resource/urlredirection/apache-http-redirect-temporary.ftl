<VirtualHost *>
    ServerName ${domainName}
    ServerAlias ${domainName}
    
    ErrorLog /var/log/apache2/${domainName}-error.log
    CustomLog /var/log/apache2/${domainName}-access.log combined
    
    RewriteEngine On
    RewriteRule /(.*) ${redirectionUrl}/$1 [R,L]
</VirtualHost>
