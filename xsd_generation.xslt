<?xml version="1.0"?>
<xsl:stylesheet version="2.0"
xmlns:zenta="http://magwas.rulez.org/zenta"
xmlns:xs="http://www.w3.org/2001/XMLSchema"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml" encoding="UTF-8" indent="yes"/>

<xsl:template match="/">
  <xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema-instance">
    <xsl:apply-templates/>
  </xs:schema>
</xsl:template>

<xsl:function name="zenta:targetOccur" as="xs:integer">
    <xsl:param name="occurs" as="xs:string?"/>
    <xsl:choose>
    <xsl:when test="string-length($occurs) = 0">-1</xsl:when>
    <xsl:otherwise>
    	<xsl:value-of select="substring-before(string($occurs), '/')"/>
    </xsl:otherwise>
    </xsl:choose>
</xsl:function>

<xsl:function name="zenta:strip">
<xsl:param name="name" as="xs:string?"/>
<xsl:value-of select="translate($name, ' -/_', '')"/>
</xsl:function>

<xsl:function name="zenta:getName">
	<xsl:param name="node"/>
	<xsl:variable name="name" select="$node/@name"/>
	<xsl:value-of select="translate($name, ' -/_', '')"/>
</xsl:function>

<xsl:function name="zenta:ternary">
<xsl:param name="condition" as="xs:boolean"/>
<xsl:param name="onTrue"/>
<xsl:param name="onFalse"/>
<xsl:choose>
	<xsl:when test="$condition">
		<xsl:value-of select="$onTrue"/>
	</xsl:when>
	<xsl:otherwise>
		<xsl:value-of select="$onFalse"/>
	</xsl:otherwise>
</xsl:choose>
</xsl:function>

<xsl:template name="createChild">
	<xsl:param name="connection"/>
	<xsl:param name="attribute" as="xs:boolean"/>
	<xsl:variable name="name" select="zenta:strip($connection/@name)"/>
	<xsl:variable name="type" select="zenta:getName(//*[@id=$connection/@target])"/>
	
	<xsl:variable name="minOccurs" select="zenta:targetOccur($connection/property[@key='minOccurs']/@value)"/>
	<xsl:variable name="maxOccurs" select="zenta:targetOccur($connection/property[@key='maxOccurs']/@value)"/>
	<xsl:if test="$maxOccurs = 1 and $attribute">
			<xs:attribute name="{$name}" type="{$type}" use="{zenta:ternary($minOccurs=1, 'required', 'optional')}"/>	
	</xsl:if>
	<xsl:if test="$maxOccurs != 1 and not($attribute)">
			<xs:element name="{$name}" type="{$type}" minOccurs="{zenta:ternary($minOccurs=1, 1, 0)}"/>	
	</xsl:if>
</xsl:template>


<xsl:template match="//element[@ancestor='application_component']">
	<xsl:variable name="name" select="./@name"/>
	<xsl:variable name="id" select="./@id"/>
	<xsl:variable name="elementId" select="zenta:strip($name)"/>
	<xs:element name="{$elementId}" type="{$elementId}"/>
	<xs:complexType name="{$elementId}">
	<xs:attribute name="name" type="string"/>
	<xsl:for-each select="//element[@source=$id or @source='application_component']">
		<xsl:call-template name="createChild">
			<xsl:with-param name="connection" select="current()"/>
			<xsl:with-param name="attribute">true</xsl:with-param>
		</xsl:call-template>
	</xsl:for-each>
	<xsl:variable name="elements">
		<xsl:for-each select="//element[@source=$id or @source='application_component']">
			<xsl:call-template name="createChild">
				<xsl:with-param name="connection" select="current()"/>
				<xsl:with-param name="attribute">0</xsl:with-param>
			</xsl:call-template>
		</xsl:for-each>
	</xsl:variable>
	<xsl:if test="$elements"> 
		<xs:sequence>
			<xsl:copy-of select="$elements"/>
		</xs:sequence>
	</xsl:if>
	</xs:complexType>
</xsl:template>

<xsl:template match="text()"/>
</xsl:stylesheet>