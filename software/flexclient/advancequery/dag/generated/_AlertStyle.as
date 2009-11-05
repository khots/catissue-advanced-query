package 
{

import flash.display.Sprite;
import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;

[ExcludeClass]

public class _AlertStyle
{

    public static function init(fbs:IFlexModuleFactory):void
    {
		var style:CSSStyleDeclaration = StyleManager.getStyleDeclaration("Alert");
    
        if (!style)
		{
			style = new CSSStyleDeclaration();
			StyleManager.setStyleDeclaration("Alert", style, false);
		}
    
        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.color = 0xffffff;
                this.backgroundAlpha = 0.9;
                this.backgroundColor = 0x869ca7;
                this.borderAlpha = 0.9;
                this.borderColor = 0x869ca7;
                this.buttonStyleName = "alertButtonStyle";
                this.paddingBottom = 2;
                this.paddingLeft = 10;
                this.paddingRight = 10;
                this.paddingTop = 2;
                this.roundedBottomCorners = true;
            };
        }
    }
}

}
