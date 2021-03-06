﻿<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <table border="1">
            <xsl:apply-templates select="person"/>
            <tr bgcolor="#9acd32">
                <th>Deti</th>
                <th>Meno</th>
                <th>Priezvisko</th>
            </tr>
            <xsl:for-each select="person/child">
                <tr>
                    <td>Dieťa
                        <xsl:value-of select="position()"/>
                    </td>
                    <td>
                        <xsl:value-of select="firstName"/>
                    </td>
                    <td>
                        <xsl:value-of select="lastName"/>
                    </td>
                </tr>
            </xsl:for-each>
        </table>
    </xsl:template>
    <xsl:template match="person">
        <tr>
            <th>Meno</th>
            <td>
                <xsl:value-of select="firstName"/>
            </td>
        </tr>

        <tr>
            <th>Priezvisko</th>
            <td>
                <xsl:value-of select="lastName"/>
            </td>
        </tr>
        <tr>
            <th>Číslo O.P.</th>
            <td>
                <xsl:value-of select="attribute::personID"/>
            </td>
        </tr>
        <tr>
            <th>Pohlavie</th>
            <td>
                <xsl:value-of select="gender"/>
            </td>
        </tr>
        <tr>
            <th>Stav</th>
            <td>
                <xsl:value-of select="status"/>
            </td>
        </tr>
        <tr>
            <th>Dátum narodenia</th>
            <td>
                <xsl:value-of select="birthDate"/>
            </td>
        </tr>
    </xsl:template>
</xsl:stylesheet>