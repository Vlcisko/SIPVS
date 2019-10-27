# Požiadavky
- JDK 13 (64bit) alebo JDK 8 (32bit) - https://filehippo.com/download_java_development_kit_32/
- DSigner v3.0 (64bit) - https://slovensko.sk/static/zep/apps/DSigner.x64.msi.zip alebo (32bit)
- Certifikát - https://test.ditec.sk/fiit/cvicenie2.zip

# Postup pre úspešný build verzia 64bit
1. v IDE nastaviť JDK 13 a popridávať .jar z priečinku /plugins
2. nainštalovať DSigner v3.0 64bit
3. nainštalovať stiahnuté certifikáty
3. registrovať pluginy 
```
	C:\Windows\Microsoft.NET\Framework\v4.0.30319\RegAsm.exe /verbose /nologo /codebase "C:\Program Files\Ditec\DSigner .NET\Ditec.Zep.DSigXades.dll"
```
```
	C:\Windows\Microsoft.NET\Framework\v4.0.30319\RegAsm.exe /verbose /nologo /codebase "C:\Program Files\Ditec\DSigner .NET\Ditec.Zep.DSigXades.XmlPlugin.dll"
```
4. build app