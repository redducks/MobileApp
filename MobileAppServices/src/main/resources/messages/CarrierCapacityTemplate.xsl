<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
            <head>
            	<title>A new Capacity Posting has been created</title>   
                <link rel="stylesheet" type="text/css" href="http://www.plspro.com/email/emailStylesheetHTML.css"/>
                <style>
					.table
					{
					   	 border-color: #999999;
					   	 border-width: 1px;
					   	 border-style: solid;
					   	 padding:3px;
					   	 border-spacing:0px;
					}
					
					.label
					{
					    color: #000000;
					    font-weight: bold
					}
                </style>
            </head>
            <body>
            	<div id="header">
                    <div id="logo" class="left">
                    </div>
                    <div id="printLogo" class="left">
                    	<img src="http://www.plspro.com/flexui/PLS/assets/images/logo.png"/>
                    </div>
                    <div id="headerImage" class="right"/>
                </div>
                <div id="main">
                	<p>A new posting has been submitted and is listed in “pending” status in PLS PRO Capacity Posting Screen.</p>
	                <table  width="600" class="table">
	                    <tr>
	                    	<td class="label"> Carrier SCAC  <xsl:text>&#160;</xsl:text> </td>
	                    	<td class="value"> <xsl:value-of select="carrierCapacity/carrierScac"/> </td>
	                    </tr>
	                    <tr>
	                    	<td class="label"> Contact Email  <xsl:text>&#160;</xsl:text> </td>
	                    	<td class="value"> <xsl:value-of select="carrierCapacity/contactEmail"/> </td>
	                    </tr>
	                    <tr>
	                         <td class="label"> Equipment Type  <xsl:text>&#160;</xsl:text> </td>
	                         <td class="value"> <xsl:value-of select="carrierCapacity/equipmentType"/><xsl:text>&#160;-&#160;</xsl:text><xsl:value-of select="carrierCapacity/equipmentTypeDesc"/></td>
	                    </tr>
	                    <tr>
	                          <td class="label"> Max Weight  <xsl:text>&#160;</xsl:text> </td>
	                          <td class="value"> <xsl:value-of select="carrierCapacity/maxWeight"/> </td>
	                    </tr>
	                    <tr>
	                          <td class="label"> # of Trucks  <xsl:text>&#160;</xsl:text> </td>
	                          <td class="value"> <xsl:value-of select="carrierCapacity/noOfTrucks"/> </td>
	                    </tr>
	                    <tr>
	                   		<td class="label"> Start Date and Time  <xsl:text>&#160;</xsl:text> </td>
	                   		<td class="value"> <xsl:value-of select="carrierCapacity/startDateTime"/> </td>
	                   	</tr>
					</table>
					<br/>
					<table class="table" width="600" >
						<tr>
							<td width="50%" class="label">Origin</td>
							<td width="50%" class="label">Destination</td>
						</tr>
						<tr>
							<td width="50%" class="value">
								<xsl:choose>
		                        	<xsl:when test="carrierCapacity/origZone and carrierCapacity/origZone!=''">
		                				<xsl:value-of select="carrierCapacity/origZone"/> 
		                        	</xsl:when>
		                        	<xsl:otherwise>
		                				<xsl:value-of select="carrierCapacity/origin"/>
		                        	</xsl:otherwise>
		                      	</xsl:choose>
							</td>
							<td width="50%" class="value">
								<xsl:choose>
		                           	<xsl:when test="carrierCapacity/destZone and carrierCapacity/destZone!=''">
		                   				 <xsl:value-of select="carrierCapacity/destZone"/>
		                           	</xsl:when>
		                           	<xsl:otherwise>
		                           		 <xsl:value-of select="carrierCapacity/preferredDest"/>
		                           	</xsl:otherwise>
								</xsl:choose>
							</td>
						</tr>
					</table>
				</div>
            </body>
        </html>
	</xsl:template>
</xsl:stylesheet>