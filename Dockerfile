# Use a slim Tomcat base for smaller image (official, secure)
FROM tomcat:10.1-jre17-temurin-focal

# Remove default Tomcat apps to avoid conflicts
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy your built WAR (build it locally first with mvn/gradle)
COPY target/myapp.war /usr/local/tomcat/webapps/ROOT.war  # Adjust path if Gradle: build/libs/myapp.war

# Expose Tomcat port
EXPOSE 8080

# Run Tomcat (default CMD from base image works)
CMD ["catalina.sh", "run"]