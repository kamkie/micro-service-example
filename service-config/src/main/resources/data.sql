-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Wersja serwera:               5.6.21-log - MySQL Community Server (GPL)
-- Serwer OS:                    Win64
-- HeidiSQL Wersja:              9.1.0.4896
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
-- Zrzucanie danych dla tabeli test.config_entry: ~53 rows (około)
/*!40000 ALTER TABLE `config_entry` DISABLE KEYS */;
DELETE FROM `config_entry`;
INSERT INTO `config_entry` (`id`, `application_name`, `profile`, `label`, `key`, `value`) VALUES
  (1, 'application', 'default', 'master', 'spring.boot.admin.username', 'admin'),
  (2, 'application', 'default', 'master', 'services.auth.username', 'user'),
  (3, 'application', 'default', 'master', 'eureka.instance.metadataMap.instanceId', '${spring.application.name}:${server.port:${random.value}}'),
  (4, 'application', 'default', 'master', 'eureka.client.registerWithEureka', 'true'),
  (5, 'application', 'default', 'master', 'eureka.client.serviceUrl.defaultZone', 'https://user:${eureka.password}@localhost:8899/eureka/'),
  (6, 'application', 'default', 'master', 'eureka.client.registryFetchIntervalSeconds', '5'),
  (7, 'application', 'default', 'master', 'security.user.password', '{cipher}AQB0dM3dSMQM5wp7GMH3ovn3wQdkbsd8saIPlslBy95zukEuIjfUvAwHq5TLdSyYstRtCdo4YH/yumB2UCGOoYJPoWu2EX9aLBDwvWiNo6ggRGVZR6vnkGlXwNSIVrzWfOo4R2AqmiJWawcdTowa3HZsSACSy2VJhAOwBkkjU119FcbaAD8tyInEvCi3oIY6cgMsBa6Tni9xyNpWMRqB0t8ERs0ihOoGRaX20OJg7sQvI+y4bwV97wOkcVkkCMeGQTiBY7gy8OaGkXw3LfsoB7wy7rv/8me0sr6AY+KhEnmS05DEKchIzKhM5s1xS43JelN+sO+eIVRsIQqJpjKl+OoTf/+5kYHq6K9R5hJGJnv6r3Z18igKeUNA8HjUpKo4Wdk='),
  (8, 'application', 'default', 'master', 'eureka.instance.healthCheckUrlPath', '/admin/health'),
  (9, 'application', 'default', 'master', 'services.auth.password', '{cipher}AQB0dM3dSMQM5wp7GMH3ovn3wQdkbsd8saIPlslBy95zukEuIjfUvAwHq5TLdSyYstRtCdo4YH/yumB2UCGOoYJPoWu2EX9aLBDwvWiNo6ggRGVZR6vnkGlXwNSIVrzWfOo4R2AqmiJWawcdTowa3HZsSACSy2VJhAOwBkkjU119FcbaAD8tyInEvCi3oIY6cgMsBa6Tni9xyNpWMRqB0t8ERs0ihOoGRaX20OJg7sQvI+y4bwV97wOkcVkkCMeGQTiBY7gy8OaGkXw3LfsoB7wy7rv/8me0sr6AY+KhEnmS05DEKchIzKhM5s1xS43JelN+sO+eIVRsIQqJpjKl+OoTf/+5kYHq6K9R5hJGJnv6r3Z18igKeUNA8HjUpKo4Wdk='),
  (10, 'application', 'default', 'master', 'management.context-path', '/admin'),
  (11, 'application', 'default', 'master', 'spring.data.rest.base-uri', '/api/v1'),
  (12, 'application', 'default', 'master', 'eureka.instance.statusPageUrlPath', '/admin/info'),
  (13, 'application', 'default', 'master', 'eureka.instance.cluster', 'DEFAULT'),
  (14, 'application', 'default', 'master', 'eureka.instance.leaseRenewalIntervalInSeconds', '10'),
  (15, 'application', 'default', 'master', 'spring.jmx.enabled', 'true'),
  (16, 'application', 'default', 'master', 'eureka.port', '8899'),
  (17, 'application', 'default', 'master', 'info.os', '${os.name}'),
  (18, 'application', 'default', 'master', 'spring.boot.admin.password', '{cipher}AQB0dM3dSMQM5wp7GMH3ovn3wQdkbsd8saIPlslBy95zukEuIjfUvAwHq5TLdSyYstRtCdo4YH/yumB2UCGOoYJPoWu2EX9aLBDwvWiNo6ggRGVZR6vnkGlXwNSIVrzWfOo4R2AqmiJWawcdTowa3HZsSACSy2VJhAOwBkkjU119FcbaAD8tyInEvCi3oIY6cgMsBa6Tni9xyNpWMRqB0t8ERs0ihOoGRaX20OJg7sQvI+y4bwV97wOkcVkkCMeGQTiBY7gy8OaGkXw3LfsoB7wy7rv/8me0sr6AY+KhEnmS05DEKchIzKhM5s1xS43JelN+sO+eIVRsIQqJpjKl+OoTf/+5kYHq6K9R5hJGJnv6r3Z18igKeUNA8HjUpKo4Wdk='),
  (19, 'application', 'default', 'master', 'eureka.client.region', 'default'),
  (20, 'application', 'default', 'master', 'eureka.client.fetchRegistry', 'true'),
  (22, 'application', 'default', 'master', 'eureka.password', '{cipher}AQB0dM3dSMQM5wp7GMH3ovn3wQdkbsd8saIPlslBy95zukEuIjfUvAwHq5TLdSyYstRtCdo4YH/yumB2UCGOoYJPoWu2EX9aLBDwvWiNo6ggRGVZR6vnkGlXwNSIVrzWfOo4R2AqmiJWawcdTowa3HZsSACSy2VJhAOwBkkjU119FcbaAD8tyInEvCi3oIY6cgMsBa6Tni9xyNpWMRqB0t8ERs0ihOoGRaX20OJg7sQvI+y4bwV97wOkcVkkCMeGQTiBY7gy8OaGkXw3LfsoB7wy7rv/8me0sr6AY+KhEnmS05DEKchIzKhM5s1xS43JelN+sO+eIVRsIQqJpjKl+OoTf/+5kYHq6K9R5hJGJnv6r3Z18igKeUNA8HjUpKo4Wdk='),
  (24, 'application', 'default', 'master', 'spring.boot.admin.url', 'https://localhost:8855'),
  (25, 'application', 'default', 'master', 'info.name', '${spring.application.name}'),
  (26, 'application', 'dev', 'master', 'spring.thymeleaf.cache', 'false'),
  (27, 'authserver', 'default', 'master', 'server.port', '8877'),
  (28, 'authserver', 'default', 'master', 'spring.datasource.driverClassName', 'com.mysql.jdbc.Driver'),
  (29, 'authserver', 'default', 'master', 'spring.datasource.password', '{cipher}AQA50llVM63XLNK1q/B2Sm1XZeBYBt8qFZuiBf+h5wq0QECY97dgN/f0gMLW3OXrF37VNFKlHcFx7qESOzDHAOQJ6s8eV+1Ik016bcNueLCfPyYUxrQZMMwKR9q4f+vrCxtcqJAR7lTCcybGatnXfhjd1T7Dutz3I2GwRHfoMKMdnGx8iHjs1YxKkXU6BQI+9ju0T72/kg1uIE9z7+absg5B+bivWNVJvPWz1GEth7qIS+E39+02to06Qb9N/ysjIKjP9gWGHP1CPiX+cUuvpIkWkNK5epbmU0RS7JfUse965h9gmFq3rUi5pHouHcRxWwDfFz4ISRAH8iUQ7eo+V7Dgy3eUcXf5AQS7biGkE4D/KkSjI8ebxq9xYmKbEDZHsoQ='),
  (30, 'authserver', 'default', 'master', 'spring.datasource.url', 'jdbc:mysql://localhost/test'),
  (31, 'authserver', 'default', 'master', 'spring.datasource.username', 'root'),
  (32, 'client', 'default', 'master', 'server.port', '8866'),
  (33, 'client', 'default', 'master', 'logging.file', 'logs/service-client.log'),
  (34, 'integration', 'default', 'master', 'server.port', '8844'),
  (35, 'integration', 'default', 'master', 'route.simple.from', 'file:source'),
  (36, 'integration', 'default', 'master', 'route.simple.to', 'file:destination'),
  (37, 'integration', 'default', 'master', 'route.simple.something', 'something'),
  (38, 'integration', 'default', 'master', 'logging.file', 'logs/service-integration.log'),
  (39, 'monitor', 'default', 'master', 'server.port', '8855'),
  (40, 'monitor', 'default', 'master', 'logging.file', 'logs/service-monitor.log'),
  (41, 'registrationserver', 'default', 'master', 'eureka.domain', 'localhost'),
  (42, 'registrationserver', 'default', 'master', 'eureka.instance.healthCheckUrlPath', '/admin/health'),
  (43, 'registrationserver', 'default', 'master', 'eureka.instance.hostname', 'localhost'),
  (44, 'registrationserver', 'default', 'master', 'eureka.instance.leaseRenewalIntervalInSeconds', '10'),
  (45, 'registrationserver', 'default', 'master', 'eureka.instance.statusPageUrlPath', '/admin/info'),
  (46, 'registrationserver', 'default', 'master', 'eureka.password', 'password'),
  (47, 'registrationserver', 'default', 'master', 'eureka.server.waitTimeInMsWhenSyncEmpty', '0'),
  (48, 'registrationserver', 'default', 'master', 'logging.file', 'logs/service-registration.log'),
  (49, 'registrationserver', 'default', 'master', 'security.user.password', '${eureka.password}'),
  (50, 'registrationserver', 'default', 'master', 'server.port', '8899'),
  (51, 'registrationserver', 'default', 'master', 'spring.cloud.config.enabled', 'false'),
  (52, 'registrationserver', 'default', 'master', 'eureka.client.serviceUrl.defaultZone', 'https://${eureka.domain}:${eureka.port}/eureka/'),
  (53, 'registrationserver', 'peer', 'master', 'server.port', '8898');
/*!40000 ALTER TABLE `config_entry` ENABLE KEYS */;

-- Zrzucanie danych dla tabeli test.users: ~50 rows (około)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
DELETE FROM `users`;
INSERT INTO `users` (`id`, `name`, `password`, `role`, `is_active`, `activation_hash`, `is_admin`, `created`) VALUES
  (1, 'admin', '$2a$10$F6WOdmfvJPjx9YiWdAYQNOmudKgTQtM37TcbNAhHukXKe9De4oSVK', 'ADMIN', 1, NULL, 1, '2014-12-14 00:01:42'),
  (2, 'user', '$2a$10$F6WOdmfvJPjx9YiWdAYQNOmudKgTQtM37TcbNAhHukXKe9De4oSVK', 'USER', 1, NULL, 1, '2014-12-14 00:01:41'),
  (3, 'kamil', '$2a$10$r9eeYyitCrbB/YWwlBhYAuAtR3y7v3MpdUGes.2lzXISRt6HLiTQC', 'ADMIN', 1, NULL, 1, '2014-12-07 23:33:43'),
  (4, 'kamkie', '$2a$10$r9eeYyitCrbB/YWwlBhYAuAtR3y7v3MpdUGes.2lzXISRt6HLiTQC', 'USER', 1, NULL, 1, '2014-12-07 15:20:14');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;


INSERT INTO `config_entry` (`application_name`, `profile`, `label`, `key`, `value`) VALUES ('application', 'default', 'master', 'server.tomcat.compression', '64');
INSERT INTO `config_entry` (`application_name`, `profile`, `label`, `key`, `value`) VALUES ('application', 'default', 'master', 'server.tomcat.compressableMimeTypes', 'application/json,application/xml,text/html,text/plain');
INSERT INTO `config_entry` (`application_name`, `profile`, `label`, `key`, `value`) VALUES ('application', 'default', 'master', 'server.ssl.key-store', 'classpath:key_store.jks');
INSERT INTO `config_entry` (`application_name`, `profile`, `label`, `key`, `value`) VALUES ('application', 'default', 'master', 'server.ssl.key-store-password', '${KEY_STORE_PASSWORD:devops}');
INSERT INTO `config_entry` (`application_name`, `profile`, `label`, `key`, `value`) VALUES ('application', 'default', 'master', 'server.ssl.key-password', '${KEY_STORE_PASSWORD:devops}');

INSERT INTO `config_entry` (`application_name`, `profile`, `label`, `key`, `value`) VALUES ('authserver', 'default', 'master', 'spring.jpa.database-platform', 'org.hibernate.dialect.MySQLDialect');
INSERT INTO `config_entry` (`application_name`, `profile`, `label`, `key`, `value`) VALUES ('authserver', 'default', 'master', 'logging.file', 'logs/service-auth.log');
