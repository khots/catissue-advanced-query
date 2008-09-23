package 
{

import flash.display.Sprite;
import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;

[ExcludeClass]

public class _TitleWindowStyle
{

    public static function init(fbs:IFlexModuleFactory):void
    {
		var style:CSSStyleDeclaration = StyleManager.getStyleDeclaration("TitleWindow");
    
        if (!style)
		{
			style = new CSSStyleDeclaration();
			StyleManager.setStyleDeclaration("TitleWindow", style, false);
		}
    
        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.backgroundColor = 0xffffff;
                this.cornerRadius = 8;
                this.dropShadowEnabled = true;
                this.paddingBottom = 4;
                this.paddingLeft = 4;
                this.paddingRight = 4;
                this.paddingTop = 4;
            };
        }
    }
}

}
