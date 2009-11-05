package 
{

import flash.display.Sprite;
import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.halo.ApplicationBackground;

[ExcludeClass]

public class _ApplicationStyle
{

    public static function init(fbs:IFlexModuleFactory):void
    {
		var style:CSSStyleDeclaration = StyleManager.getStyleDeclaration("Application");
    
        if (!style)
		{
			style = new CSSStyleDeclaration();
			StyleManager.setStyleDeclaration("Application", style, false);
		}
    
        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.backgroundColor = 0x869ca7;
                this.backgroundGradientAlphas = [1, 1];
                this.backgroundImage = mx.skins.halo.ApplicationBackground;
                this.backgroundSize = "100%";
                this.horizontalAlign = "center";
                this.paddingBottom = 24;
                this.paddingLeft = 24;
                this.paddingRight = 24;
                this.paddingTop = 24;
            };
        }
    }
}

}
