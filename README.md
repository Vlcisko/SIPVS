# Požiadavky
- JDK 8 (32bit) - Oracle alebo https://filehippo.com/download_java_development_kit_32/
- DSigner v3.0 (32bit)- https://slovensko.sk/static/zep/apps/DSigner.x86.msi.zip
- Certifikát - https://test.ditec.sk/fiit/cvicenie2.zip

# Postup pre úspešný build
1. v IDE nastaviť JDK 8(32bit)
2. nainštalovať DSigner v3.0
3. nainštalovať stiahnuté certifikáty
3. registrovať pluginy 
	C:\Windows\Microsoft.NET\Framework\v4.0.30319\RegAsm.exe /verbose /nologo /codebase "C:\Program Files (x86)\Ditec\DSigXades\Ditec.Zep.DSigXades.dll"
	C:\Windows\Microsoft.NET\Framework\v4.0.30319\RegAsm.exe /verbose /nologo /codebase "C:\Program Files (x86)\Ditec\DSigXades\Ditec.Zep.DSigXades.XmlPlugin.dll"
4. build app