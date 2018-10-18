<?xml version="1.0" encoding="ISO-8859-1"?>
<!DOCTYPE xml>
<xsl:stylesheet version="2.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:zenta="http://magwas.rulez.org/zenta"
    xmlns:zentatools="java:org.rulez.magwas.zentatools.XPathFunctions"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="xml" version="1.0" encoding="utf-8" indent="yes" omit-xml-declaration="yes"/>

    <xsl:include href="xslt/functions.xslt"/>

	<xsl:param name="outputbase"/>

  <xsl:function name="zenta:getAnnotation">
  	<xsl:param name="context"/>
  	<xsl:param name="annotationName"/>
  	<xsl:copy-of select="normalize-space($context/annotation[@name=$annotationName]|$context/../annotation[@name=$annotationName]/argument/value/text())"/>
  </xsl:function>

  <xsl:template match="@*|*|processing-instruction()|comment()|text()" mode="#all">
      <xsl:apply-templates select="*|@*|text()|processing-instruction()|comment()" mode="#current"/>
  </xsl:template>

  <xsl:template match="method[annotation/@name='Test']" mode="gatherTestCases">
	<testcase>
		<xsl:attribute name="name" select="@name"/>
		<xsl:attribute name="doc" select="replace(@name,'_', ' ')"/>
		<xsl:variable name="feature" select="zenta:getAnnotation(.,'tested_feature')"/>
		<xsl:variable name="operation" select="zenta:getAnnotation(.,'tested_operation')"/>
		<xsl:variable name="behaviour" select="zenta:getAnnotation(.,'tested_behaviour')"/>
		<xsl:attribute name="feature" select="$feature"/>
		<xsl:attribute name="operation" select="$operation"/>
		<xsl:attribute name="behaviour" select="$behaviour"/>
		<xsl:attribute name="modelmatch" select="concat($feature,'/', $operation, '; ', $behaviour)"/>
	</testcase>
  </xsl:template>

  <xsl:template match="/" mode="gatherTestCases">
	<testcases>
      <xsl:apply-templates select="*|text()|processing-instruction()|comment()" mode="#current"/>
	</testcases>
  </xsl:template>

  <xsl:function name="zenta:listBehaviours">
  	<xsl:param name="testcases"/>
  	<xsl:param name="feature"/>
  	<xsl:param name="operation"/>
	<xsl:for-each select="distinct-values($testcases//testcase[@feature=$feature and @operation=$operation]//@behaviour)">
		<xsl:variable name="behaviour" select="."/>
		<behaviour>
			<xsl:attribute name="name" select="$behaviour"/>
			<xsl:copy-of select="$testcases//testcase[@feature=$feature and @operation=$operation and @behaviour=$behaviour]"/>
		</behaviour>
	</xsl:for-each>
  </xsl:function>

  <xsl:function name="zenta:listOperations">
  	<xsl:param name="testcases"/>
  	<xsl:param name="feature"/>
	<xsl:for-each select="distinct-values($testcases//testcase[@feature=$feature]//@operation)">
		<xsl:variable name="operation" select="."/>
		<operation>
			<xsl:attribute name="name" select="$operation"/>
			<xsl:copy-of select="zenta:listBehaviours($testcases,$feature,$operation)"/>
		</operation>
	</xsl:for-each>
  </xsl:function>

  <xsl:function name="zenta:listBehaviours">
  	<xsl:param name="testcases"/>
  	<features>
	  	<xsl:for-each select="distinct-values($testcases//@feature)">
	  		<xsl:variable name="feature" select="."/>
		  	<feature>
		  		<xsl:attribute name="name" select="$feature"/>
  				<xsl:copy-of select="zenta:listOperations($testcases,$feature)"/>
		  	</feature>
	  	</xsl:for-each>
  	</features>
  </xsl:function>

  <xsl:template match="/">
  	<xsl:variable name="testcases">
      <xsl:apply-templates select="/" mode="gatherTestCases"/>
  	</xsl:variable>
  	<xsl:variable name="behaviours">
		<xsl:copy-of select="zenta:listBehaviours($testcases)"/>
  	</xsl:variable>
	<xsl:result-document href="{$outputbase}implementedTestCases.xml">
		<xsl:copy-of select="$testcases"/>
	</xsl:result-document>
	<xsl:result-document href="{$outputbase}implementedBehaviours.xml">
		<xsl:copy-of select="$behaviours"/>
	</xsl:result-document>
	<xsl:result-document href="{$outputbase}implementedBehaviours.docbook">
		<xsl:apply-templates select="$behaviours" mode="behaviourDocbook"/>
	</xsl:result-document>
  </xsl:template>
  
  <xsl:template match="features" mode="behaviourDocbook">
  	<article version="5.0">
  		<title>Implemented behaviours</title>
      <xsl:apply-templates select="*" mode="#current"/>
  	</article>
  </xsl:template>

  <xsl:template match="feature|operation" mode="behaviourDocbook">
  	<section>
  		<xsl:variable name="titlestring" select="concat(node-name(.),': ',@name)"/>
  		<xsl:attribute name="id" select="$titlestring"/>
  		<title>
  			<xsl:value-of select="$titlestring"/>
  		</title>
      <xsl:apply-templates select="*" mode="#current"/>
  	</section>
  </xsl:template>

  <xsl:template match="behaviour" mode="behaviourDocbook">
  	<section>
  		<xsl:attribute name="id" select="@name"/>
  		<title>
  			<xsl:value-of select="@name"/>
  		</title>
  		<para>
  			<itemizedlist>
      			<xsl:apply-templates select="*" mode="#current"/>
  			</itemizedlist>
  		</para>
  	</section>
  </xsl:template>

  <xsl:template match="testcase" mode="behaviourDocbook">
  	<listitem><para>
  		<xsl:value-of select="@doc"/>
  	</para></listitem>
  </xsl:template>

</xsl:stylesheet>

