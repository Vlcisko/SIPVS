# Požiadavky
- JDK 13 (64bit) alebo JDK 8 (32bit) - https://filehippo.com/download_java_development_kit_32/
- DSigner v3.0 (64bit) - https://slovensko.sk/static/zep/apps/DSigner.x64.msi.zip alebo (32bit)
- Certifikát - https://test.ditec.sk/fiit/cvicenie2.zip
- Apache Maven 3 - http://apache.mediamirrors.org/maven/maven-3/3.6.2/binaries/apache-maven-3.6.2-bin.zip
- JavaFX 13.0.1 : http://gluonhq.com/download/javafx-13.0.1-sdk-windows/
- Eclipse 2019-09 (VOLITEĽNÉ)

# Postup pre úspešný build verzia 64bit

1. V IDE nastaviť JDK 13 
2. Nainštalovať DSigner v3.0 64bit
3. Nainštalovať stiahnuté certifikáty
4. Pridať *.jar ako User Libraries z 
   - ..\plugins 
5. Pridať jacob.dll do 'resources' zo src\main\resources\dll
6. Stiahuť a pridať do cesty JavaFX 13.0.1
   - spúšťat main.Main s argumentom (--module-path "C:\cestaKJavaFX\javafx-sdk-13.0.1\lib" --add-modules javafx.controls,javafx.fxml)
   - alebo pouziť pridanie module-info a vypisanie potrebných modulov
7. Spustenie Maven Clean na:
   - stiahnutie potrebnych kniznic
   - vygenerovanie kodu z TS.wsdl
8. nastavit cestu k vlastnemu .NETu otvorenim cmd a zadania nasledujucich riadkov (CESTU TREBA UPRAVIT!)
```
	C:\Windows\Microsoft.NET\Framework\v4.0.30319\RegAsm.exe /verbose /nologo /codebase "C:\Program Files\Ditec\DSigner .NET\Ditec.Zep.DSigXades.dll"
```
```
	C:\Windows\Microsoft.NET\Framework\v4.0.30319\RegAsm.exe /verbose /nologo /codebase "C:\Program Files\Ditec\DSigner .NET\Ditec.Zep.DSigXades.XmlPlugin.dll"
```



## EXTRA

- Ak by JavaFx hadzalo nejake errory, treba stiahnut javaFX a skopirovat obsah zlozky \bin do \bin zlozky, ktoru mate vo windowse ulozenu ako java PATH, teda napr. "C:\Program Files\Java\jdk-13.0.1\bin"
	http://gluonhq.com/download/javafx-13.0.1-sdk-windows/