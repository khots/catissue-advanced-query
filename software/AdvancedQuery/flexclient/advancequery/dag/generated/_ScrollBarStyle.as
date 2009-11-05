package 
{

import flash.display.Sprite;
import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.halo.ScrollArrowSkin;
import mx.skins.halo.ScrollTrackSkin;
import mx.skins.halo.ScrollThumbSkin;

[ExcludeClass]

public class _ScrollBarStyle
{

    public static function init(fbs:IFlexModuleFactory):void
    {
		var style:CSSStyleDeclaration = StyleManager.getStyleDeclaration("ScrollBar");
    
        if (!style)
		{
			style = new CSSStyleDeclaration();
			StyleManager.setStyleDeclaration("ScrollBar", style, false);
		}
    
        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.borderColor = 0xb7babc;
                this.cornerRadius = 4;
                this.downArrowDisabledSkin = mx.skins.halo.ScrollArrowSkin;
                this.downArrowDownSkin = mx.skins.halo.ScrollArrowSkin;
                this.downArrowOverSkin = mx.skins.halo.ScrollArrowSkin;
                this.downArrowUpSkin = mx.skins.halo.ScrollArrowSkin;
                this.thumbDownSkin = mx.skins.halo.ScrollThumbSkin;
                this.thumbOverSkin = mx.skins.halo.ScrollThumbSkin;
                this.thumbUpSkin = mx.skins.halo.ScrollThumbSkin;
                this.trackColors = [0x94999b, 0xe7e7e7];
                this.trackSkin = mx.skins.halo.ScrollTrackSkin;
                this.upArrowDisabledSkin = mx.skins.halo.ScrollArrowSkin;
                this.upArrowDownSkin = mx.skins.halo.ScrollArrowSkin;
                this.upArrowOverSkin = mx.skins.halo.ScrollArrowSkin;
                this.upArrowUpSkin = mx.skins.halo.ScrollArrowSkin;
            };
        }
    }
}

}
