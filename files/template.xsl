<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:fo="http://www.w3.org/1999/XSL/Format">
  <xsl:output method="xml" indent="yes"/>

  <xsl:attribute-set name="smallerFont">
    <xsl:attribute name="font-size">14pt</xsl:attribute>
    <xsl:attribute name="font-weight">bold</xsl:attribute>
  </xsl:attribute-set>
  <xsl:attribute-set name="largerFont">
    <xsl:attribute name="font-size">30pt</xsl:attribute>
    <xsl:attribute name="font-weight">bold</xsl:attribute>
  </xsl:attribute-set>

  <xsl:template match="/">
    <fo:root>
      <fo:layout-master-set>
        <fo:simple-page-master master-name="A4-portrait"
          page-height="29.7cm" page-width="21.0cm" margin="2cm">
          <fo:region-body/>
        </fo:simple-page-master>
      </fo:layout-master-set>
      <fo:page-sequence force-page-count="auto" master-reference="A4-portrait">
        <fo:flow flow-name="xsl-region-body">
          <fo:block text-align="center" xsl:use-attribute-sets="largerFont">
            <xsl:value-of select="document/title"/>
          </fo:block>
          <fo:block space-before="10pt">
            <xsl:value-of select="document/body/description"/>
          </fo:block>
          <fo:block>
            <fo:external-graphic
              src="file:///path_to_svg//chart.svg"
              content-height="scale-to-fit" height="6.00in" content-width="6.50in"
              scaling="non-uniform"/>
          </fo:block>
          <fo:block text-align="center" space-after="10pt">
            <xsl:value-of select="document/body/chart-title"/>
          </fo:block>

          <fo:table table-layout="fixed" width="100%" font-size="10pt" text-align="center" display-align="center"
            space-after="5mm">
            <fo:table-column column-width="proportional-column-width(25)"/>
            <fo:table-column column-width="proportional-column-width(25)"/>
            <fo:table-column column-width="proportional-column-width(25)"/>
            <fo:table-column column-width="proportional-column-width(25)"/>
            <fo:table-body font-size="95%">
              <fo:table-row height="14mm">
                <fo:table-cell>
                  <fo:block>Count of symbols without spaces</fo:block>
                </fo:table-cell>
                <fo:table-cell>
                  <fo:block>Count of symbols with spaces</fo:block>
                </fo:table-cell>
                <fo:table-cell>
                  <fo:block>Count of words</fo:block>
                </fo:table-cell>
                <fo:table-cell>
                  <fo:block>Count of sentences</fo:block>
                </fo:table-cell>
              </fo:table-row>
              <fo:table-row>
                <fo:table-cell>
                  <fo:block>
                    <xsl:value-of select="document/body/table/symbols-none-spaces"/>
                  </fo:block>
                </fo:table-cell>
                <fo:table-cell>
                  <fo:block>
                    <xsl:value-of select="document/body/table/symbols-with-spaces"/>
                  </fo:block>
                </fo:table-cell>
                <fo:table-cell>
                  <fo:block>
                    <xsl:value-of select="document/body/table/count-words"/>
                  </fo:block>
                </fo:table-cell>
                <fo:table-cell>
                  <fo:block>
                    <xsl:value-of select="document/body/table/count-sentences"/>
                  </fo:block>
                </fo:table-cell>
              </fo:table-row>
            </fo:table-body>
          </fo:table>
        </fo:flow>
      </fo:page-sequence>
    </fo:root>
  </xsl:template>
</xsl:stylesheet>