echo stopping services
systemctl stop cookbook
echo removing previously installed version
rm /var/www/services/cookbook/cookbook.jar
echo installing new version
cp /var/www/services/cookbook/${project.artifactId}-${project.version}-executable.jar /var/www/services/cookbook/cookbook.jar
echo setting grants on executables
chown cookbook:cookbook /var/www/services/cookbook/cookbook.jar
echo setting up service
cp /var/www/services/cookbook/cookbook.service /etc/systemd/system/
echo setting grants on services
chmod u+rwx /etc/systemd/system/cookbook.service
echo reloading service configuration
systemctl daemon-reload
echo enabling services to start on server boot
systemctl enable cookbook
echo starting service
systemctl start cookbook