package 
{

import flash.display.Sprite;
import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.halo.HaloFocusRect;
import mx.skins.halo.HaloBorder;

[ExcludeClass]

public class _globalStyle
{

    public static function init(fbs:IFlexModuleFactory):void
    {
		var style:CSSStyleDeclaration = StyleManager.getStyleDeclaration("global");
    
        if (!style)
		{
			style = new CSSStyleDeclaration();
			StyleManager.setStyleDeclaration("global", style, false);
		}
    
        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.color = 0x0b333c;
                this.disabledColor = 0xaab3b3;
                this.dropShadowColor = 0x000000;
                this.errorColor = 0xff0000;
                this.fontAntiAliasType = "advanced";
                this.fontFamily = "Verdana";
                this.fontGridFitType = "pixel";
                this.fontSharpness = 0;
                this.fontSize = 10;
                this.fontStyle = "normal";
                this.fontThickness = 0;
                this.fontWeight = "normal";
                this.horizontalGridLineColor = 0xf7f7f7;
                this.modalTransparency = 0.5;
                this.modalTransparencyBlur = 3;
                this.modalTransparencyColor = 0xdddddd;
                this.modalTransparencyDuration = 100;
                this.selectionDisabledColor = 0xdddddd;
                this.textAlign = "left";
                this.textIndent = 0;
                this.textRollOverColor = 0x2b333c;
                this.textSelectedColor = 0x2b333c;
                this.themeColor = 0x009dff;
                this.verticalGridLineColor = 0xd5dddd;
                this.backgroundAlpha = 1.0;
                this.backgroundSize = "auto";
                this.bevel = true;
                this.borderAlpha = 1.0;
                this.borderCapColor = 0x919999;
                this.borderColor = 0xb7babc;
                this.borderSides = "left top right bottom";
                this.borderSkin = mx.skins.halo.HaloBorder;
                this.borderStyle = "inset";
                this.borderThickness = 1;
                this.buttonColor = 0x6f7777;
                this.closeDuration = 250;
                this.cornerRadius = 0;
                this.dropShadowEnabled = false;
                this.embedFonts = false;
                this.fillAlphas = [0.6, 0.4, 0.75, 0.65];
                this.fillColor = 0xffffff;
                this.fillColors = [0xffffff, 0xcccccc, 0xffffff, 0xeeeeee];
                this.filled = true;
                this.focusAlpha = 0.4;
                this.focusBlendMode = "normal";
                this.focusRoundedCorners = "tl tr bl br";
                this.focusSkin = mx.skins.halo.HaloFocusRect;
                this.focusThickness = 2;
                this.highlightAlphas = [0.3, 0];
                this.horizontalAlign = "left";
                this.horizontalGap = 8;
                this.horizontalGridLines = false;
                this.indentation = 17;
                this.indicatorGap = 14;
                this.leading = 2;
                this.openDuration = 250;
                this.paddingBottom = 0;
                this.paddingLeft = 0;
                this.paddingRight = 0;
                this.paddingTop = 0;
                this.repeatDelay = 500;
                this.repeatInterval = 35;
                this.roundedBottomCorners = false;
                this.selectionDuration = 250;
                this.shadowCapColor = 0xd5dddd;
                this.shadowColor = 0xeeeeee;
                this.shadowDirection = "center";
                this.shadowDistance = 2;
                this.strokeWidth = 1;
                this.stroked = false;
                this.useRollOver = true;
                this.verticalAlign = "top";
                this.verticalGap = 6;
                this.verticalGridLines = true;
            };
        }
    }
}

}
