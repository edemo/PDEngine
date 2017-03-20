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

  <xsl:template match="/">
    <xsl:variable name="testcases">
    	<testcases>
        	<xsl:call-template name="testcases"/>
    	</testcases>
    </xsl:variable>
	<xsl:result-document href="{$outputbase}testcases.txt">
		<xsl:copy-of select="zenta:writeTestcasesAsText($testcases)"/>
	</xsl:result-document>
	<xsl:result-document href="{$outputbase}testcases.xml">
		<xsl:copy-of select="$testcases"/>
	</xsl:result-document>
  </xsl:template>

<xsl:function name="zenta:writeTestcasesAsText">
	<xsl:param name="testcases"/>
    <xsl:for-each select="$testcases//testcase">
----------------------------------------------------------------------------
Behaviour: <xsl:value-of select="@name"/>
 @tested_feature("<xsl:value-of select="@feature"/>")
 @tested_operation("<xsl:value-of select="@operation"/>")
 @tested_behaviour("<xsl:value-of select="@testcase"/>")
<xsl:if test="@addtestcase">
 @tested_aspect(<xsl:value-of select="@addtestcase"/>)
</xsl:if>
<xsl:text> </xsl:text><xsl:value-of select="replace(.,'\\n','\\n  ')"/>
<xsl:text>
</xsl:text>
    </xsl:for-each>
</xsl:function>

  <xsl:template name="testcases">
    <xsl:variable name="root" select="/"/>
    <xsl:for-each select="//element[@xsi:type='Business Function']">
        <xsl:variable name="feature" select="."/>
        <xsl:for-each select="zenta:neighboursOnPath(/,$feature,'contains,1')">
            <xsl:variable name="operation" select="."/>
                <xsl:for-each select="zenta:neighboursOnPath($root,$operation,'does/is done by,1')">
                    <xsl:variable name="testcase" select="."/>
                    <testcase name="{concat($feature/@name,'/', $operation/@name, '; ', $testcase/@name)}"
                        feature="{$feature/@name}"
                        operation="{$operation/@name}"
                        testcase="{$testcase/@name}"
                        featureid="{$feature/@id}"
                        operationid="{$operation/@id}"
                        testcaseid="{$testcase/@id}">
                        <xsl:copy-of select="$testcase/documentation/(text()|*)"/>
                    </testcase>
                    <xsl:for-each select="zenta:neighboursOnPath($root,$testcase,'is tested by/tests,2,testcase')">
                        <xsl:variable name="addtestcase" select="."/>
                        <testcase name="{concat($feature/@name,'/', $operation/@name, '; ', $testcase/@name, '/', $addtestcase/@name)}"
                            feature="{concat(
                                zenta:neighboursOnPath($root,$feature,'contains/iscontained by,2')/@name,'.',
                                $feature/@name)}"
                            operation="{$operation/@name}"
                            testcase="{$testcase/@name}"
                            additionaltestcase="{$addtestcase/@name}">
                            <xsl:copy-of select="$testcase/documentation/(text()|*)"/>.
                            <xsl:copy-of select="$addtestcase/documentation/(text()|*)"/>
                        </testcase>
                    </xsl:for-each>
                </xsl:for-each>
        </xsl:for-each>
    </xsl:for-each>
  </xsl:template>

</xsl:stylesheet>

