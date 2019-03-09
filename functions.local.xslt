<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xml>
<xsl:stylesheet
			xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
			version='2.0'
			xmlns:xhtml="http://www.w3.org/TR/xhtml1/transitional"
			xmlns:fn="http://www.w3.org/2005/xpath-functions"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xmlns:zenta="http://magwas.rulez.org/zenta">

	<xsl:output method="xml" version="1.0" encoding="utf-8" indent="yes" omit-xml-declaration="yes"/>

	<!--xsl:template match="element[@xsi:type='Ada:varlistentry']" mode="elementTitle">
		<xsl:value-of select="@name"/>
	</xsl:template>

    <xsl:template match="element[@id='basicobject']|connection[@id='basicrelation']" mode="varlistentry">
    </xsl:template>

    <xsl:template match="folder[property[@key='display']/@value='hidden']" mode="varlist"/-->

    <xsl:function name="zenta:engineErrorDescription">
    	<xsl:param name="entry"/>
    	<xsl:param name="other"/>
    	<xsl:variable name="testcase" select="$entry/object/testcase"/>
    	Unimplemented behaviour.
    	<variablelist>
    		<varlistentry>
    			<term>related feature:</term>
    			<listitem><para>
    				<ulink url="#{$testcase/@featureid}"><xsl:value-of select="$testcase/@feature"/></ulink>
    			</para></listitem>
    		</varlistentry>
    		<varlistentry>
    			<term>related operation:</term>
    			<listitem><para>
    				<ulink url="#{$testcase/@operationid}"><xsl:value-of select="$testcase/@operation"/></ulink>
    			</para></listitem>
    		</varlistentry>
    		<varlistentry>
    			<term>related behaviour:</term>
    			<listitem><para>
    				<ulink url="#{$testcase/@testcaseid}"><xsl:value-of select="$testcase/@testcase"/></ulink>
    			</para></listitem>
    		</varlistentry>
    	</variablelist>
    	<xsl:choose>
    		<xsl:when test="$testcase/@featureid">
    	issue markup:
<para>
Behaviour: <xsl:value-of select="$testcase/@feature"/>/<xsl:value-of select="$testcase/@operation"/>;<xsl:value-of select="$testcase/@testcase"/>
</para><para>
 @tested_feature("<xsl:value-of select="$testcase/@feature"/>")
</para><para>
 @tested_operation("<xsl:value-of select="$testcase/@operation"/>")
</para><para>
 @tested_behaviour("<xsl:value-of select="$testcase/@testcase"/>")
</para><para>

<xsl:value-of select="$testcase"/>
</para><para>
    	[Deviation in model](http://adadocs.demokracia.rulez.org/PDEngine/edemo/master/index.html#<xsl:value-of select="zenta:engineErrorId($entry)"/>)
</para>
    		</xsl:when>
    		<xsl:otherwise>
    	issue markup:
    	[Deviation in model](http://adadocs.demokracia.rulez.org/PDEngine/edemo/master/index.html#<xsl:value-of select="$entry//object/testcase/@name"/>)
    		</xsl:otherwise>
    	</xsl:choose>
    </xsl:function>
    <xsl:function name="zenta:engineErrorId">
    	<xsl:param name="entry"/>
    	<xsl:variable name="testcase" select="$entry/object/testcase"/>
    	<xsl:choose>
    		<xsl:when test="$testcase/@featureid">
    			<xsl:value-of select="concat($testcase/@featureid,'-',$testcase/@operationid,'-',$testcase/@testcaseid)"/>
    		</xsl:when>
    		<xsl:otherwise>
    			<xsl:value-of select="$testcase/@name"/>
    		</xsl:otherwise>
    	</xsl:choose>
    </xsl:function>
    <xsl:function name="zenta:engineErrorTitle">
    	<xsl:param name="entry"/>
    	<xsl:variable name="testcase" select="$entry/object/testcase"/>
    	<xsl:value-of select="concat('Unimplemented feature',':',$testcase/@name)"/>
    </xsl:function>
</xsl:stylesheet>

