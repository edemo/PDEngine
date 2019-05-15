<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="xml" version="1.0" encoding="utf-8" indent="yes" omit-xml-declaration="yes"/>

    <xsl:param name="containerId"/>
    <xsl:param name="datafile"/>

  <xsl:template match="@*|*|processing-instruction()|comment()">
    <xsl:copy>
          <xsl:apply-templates select="*|@*|text()|processing-instruction()|comment()"/>
    </xsl:copy>
  </xsl:template>

  <xsl:template match="*" priority="10">
      <xsl:choose>
        <xsl:when test="@id = $containerId">
            <xsl:message select="'inserting'"/>
            <xsl:copy-of select="document($datafile)"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:copy>
            <xsl:apply-templates select="*|@*|text()|processing-instruction()|comment()"/>
          </xsl:copy>
        </xsl:otherwise>
      </xsl:choose>
  </xsl:template>

</xsl:stylesheet>

