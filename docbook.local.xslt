<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xml>
<xsl:stylesheet
			xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
			version='2.0'
			xmlns:html="http://www.w3.org/TR/xhtml1/transitional"
			xmlns:fn="http://www.w3.org/2005/xpath-functions"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xmlns:zenta="http://magwas.rulez.org/zenta"
            >
			<!--exclude-result-prefixes="#default"-->

	<xsl:output method="xml" version="1.0" encoding="utf-8" indent="yes" omit-xml-declaration="yes"/>

	<xsl:template match="element[@xsi:type='Ada:varlistentry']" mode="elementTitle">
		<xsl:value-of select="@name"/>
	</xsl:template>

    <xsl:template match="element[@id='basicobject']|connection[@id='basicrelation']" mode="varlistentry" priority="4">
    </xsl:template>

        <!--xsl:template match="element[@xsi:type!='zenta:ZentaDiagramModel' and @xsi:type!='zenta:SketchModel']"
                mode="varlistentry" priority="5"/-->
    <xsl:template match="folder[property[@key='display']/@value='hidden']" mode="varlist"/>

        <xsl:template match="element[@xsi:type!='zenta:ZentaDiagramModel' and @xsi:type!='zenta:SketchModel']"
                mode="elementDetails" priority="5">
                <para>
                        <xsl:value-of select="concat('Is ', zenta:articledName(//element[@id=current()/@ancestor],'no'),'.')"/>
                </para>
                <para>
                        <xsl:copy-of select="documentation/(*|text())"/>
                </para>
                <para>
                <xsl:if test="value">
                        connections:
                        <itemizedlist>
                                <xsl:for-each select="value">
                                        <listitem>
                                                <xsl:variable name="atleast">
                                                        <xsl:if test="number(@minOccurs) > 0">
                                                                <xsl:value-of select="if (number(@minOccurs) > 0) then concat('at least ',@minOccurs,' ') else ''"/>
                                                        </xsl:if>
                                                </xsl:variable>
                                                <xsl:variable name="atmost">
                                                                <xsl:value-of select="if (number(@maxOccurs) > 0) then concat('at most ',@maxOccurs,' ') else '' "/>
                                                </xsl:variable>
                                                <xsl:variable name="numbers" select="if ($atmost!='' and $atleast!='') then concat($atleast,'and ',$atmost) else concat(
$atleast,$atmost)"/>
                                                <!--xsl:value-of select="../@name"/-->
                                                It 
                                                <xsl:value-of select="concat(' ',
                                                                zenta:relationName(.),' ',
                                                                if (@template='true') then $numbers else ''
                                                                )"/>
                                                <link linkend="{@target}">
                                                        <xsl:value-of select="@name"/>
                                                </link>
                                        </listitem>
                                </xsl:for-each>
                        </itemizedlist>
                </xsl:if>
                </para>
        </xsl:template>
</xsl:stylesheet>
