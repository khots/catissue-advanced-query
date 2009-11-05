package 
{

import flash.display.Sprite;
import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.halo.ToolTipBorder;

[ExcludeClass]

public class _ToolTipStyle
{

    public static function init(fbs:IFlexModuleFactory):void
    {
		var style:CSSStyleDeclaration = StyleManager.getStyleDeclaration("ToolTip");
    
        if (!style)
		{
			style = new CSSStyleDeclaration();
			StyleManager.setStyleDeclaration("ToolTip", style, false);
		}
    
        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.fontSize = 9;
                this.backgroundAlpha = 0.95;
                this.backgroundColor = 0xffffcc;
                this.borderColor = 0x919999;
                this.borderSkin = mx.skins.halo.ToolTipBorder;
                this.borderStyle = "toolTip";
                this.cornerRadius = 2;
                this.paddingBottom = 2;
                this.paddingLeft = 4;
                this.paddingRight = 4;
                this.paddingTop = 2;
                this.shadowColor = 0x000000;
            };
        }
    }
}

}
