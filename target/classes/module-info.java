
module SIPVS {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.fxml;
    requires dsigner.plugin.xml;
    requires dsigner;

    requires org.apache.santuario.xmlsec;
    requires slf4j.api;
    requires commons.logging;
    requires java.xml.bind;
    requires com.sun.xml.bind;
    requires java.xml;
    requires jacob;

    opens sipvs;
}
