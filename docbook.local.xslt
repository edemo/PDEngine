<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
			xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
			version='2.0'
			xmlns="http://www.w3.org/TR/xhtml1/transitional"
			xmlns:fn="http://www.w3.org/2005/xpath-functions"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xmlns:zenta="http://magwas.rulez.org/zenta"
			exclude-result-prefixes="#default">

	<xsl:output method="xml" version="1.0" encoding="utf-8" indent="yes" omit-xml-declaration="yes"/>

	<!--xsl:template match="element[@xsi:type='Ada:varlistentry']" mode="elementTitle">
		<xsl:value-of select="@name"/>
	</xsl:template>

    <xsl:template match="element[@id='basicobject']|connection[@id='basicrelation']" mode="varlistentry">
    </xsl:template>

    <xsl:template match="folder[property[@key='display']/@value='hidden']" mode="varlist"/-->
    
</xsl:stylesheet>

