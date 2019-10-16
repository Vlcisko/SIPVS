<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

<xsl:template match="/">
  <table border="1">
    <tr>
      <th>Meno</th>
      <th>Priezvisko</th>
      <th>Číslo O.P.</th>
      <th>Pohlavie</th>
  	  <th>Stav</th>
  	  <th>Dátum narodenia</th>  	  
    </tr>
    <xsl:apply-templates select="person"/>
    <tr bgcolor="#9acd32">
    	<th>Deti</th>
      <th>Meno</th>
      <th>Priezvisko</th>
    </tr>
    <xsl:for-each select="person/child">
    <tr>
      <td>Dieťa <xsl:value-of select="position()" /></td>
      <td><xsl:value-of select="firstName"/></td>
      <td><xsl:value-of select="lastName"/></td>
    </tr>
    </xsl:for-each>
  </table>
</xsl:template>

<xsl:template match="person">
  <tr>
    <td><xsl:value-of select="firstName"/></td>
    <td><xsl:value-of select="lastName"/></td>
    <td><xsl:value-of select="attribute::personID"/></td>
    <td><xsl:value-of select="gender"/></td>
    <td><xsl:value-of select="status"/></td>
	  <td><xsl:value-of select="birthDate"/></td>
  </tr>
</xsl:template>


</xsl:stylesheet>