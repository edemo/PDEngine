<?xml version="1.0" encoding="ISO-8859-1"?>
<!DOCTYPE xml>
<xsl:stylesheet version="2.0"
	xmlns="http://pmd.sourceforge.net/report/2.0.0"
	xmlns:pmd="http://pmd.sourceforge.net/report/2.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:zenta="http://magwas.rulez.org/zenta"
    xmlns:zentatools="java:org.rulez.magwas.zentatools.XPathFunctions"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	exclude-result-prefixes="pmd zenta zentatools">

	<xsl:output method="xml" version="1.0" encoding="utf-8" indent="yes" omit-xml-declaration="yes"/>

  <xsl:variable name="cpd" select="document('target/cpd.xml')"/>
  <xsl:variable name="pmd" select="document('target/pmd.xml')"/>


  <xsl:template match="/" >
  	<pmd>
  	<xsl:namespace name="" select="'http://pmd.sourceforge.net/report/2.0.0'"/>
  	<xsl:copy-of select="/*/@*"/>
  	<xsl:for-each select="distinct-values($cpd//file/@path|$pmd//pmd:file/@name)">
  		<xsl:variable name="filename" select="."/>
  		<file>
  			<xsl:attribute name="name" select="$filename"/>
  			<xsl:copy-of select="$pmd//pmd:file[@name=$filename]/pmd:violation"/>
	  		<xsl:for-each select="$cpd//duplication[file/@path=$filename]">
	  			<xsl:variable name="duplication" select="."/>
	  			<xsl:variable name="currentfile" select="$duplication/file[@path=$filename][1]"/>
	  			<violation
	  				rule="Code Duplication"
	  				ruleset="Code Style"
	  				begincolumn="1"
	  				endcolumn="40"
	  				externalInfoUrl="https://en.wikipedia.org/wiki/Duplicate_code"
	  				package="org.rulez.demokracia.pdengine"
	  				class="VoteRegistry"
	  				method="setVoteParameters"
	  				variable="vote"
	  				priority="5">
	  				<xsl:attribute name="beginline" select="$currentfile/@line"/>
	  				<xsl:attribute name="endLine" select="$currentfile/@line + $duplication/@lines"/>
	  				Code duplication (<xsl:value-of select="$duplication/@lines"/> lines, <xsl:value-of select="$duplication/@tokens"/> tokens):
	  				<xsl:text>
	  				</xsl:text>
	  				<xsl:for-each select="$duplication/file">
	  				<xsl:value-of select="concat(@path,', line ',@line)"/>
	  				<xsl:text>
	  				</xsl:text>
	  				</xsl:for-each>
	  			</violation>
	  		</xsl:for-each>
  		</file>
  	</xsl:for-each>
  	<foo/>
  	</pmd>
  </xsl:template>

</xsl:stylesheet>

