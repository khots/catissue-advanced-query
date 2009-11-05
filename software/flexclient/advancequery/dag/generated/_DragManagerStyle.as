package 
{

import flash.display.Sprite;
import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.halo.DefaultDragImage;

[ExcludeClass]

public class _DragManagerStyle
{
    [Embed(_pathsep='true', _resolvedSource='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', source='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', _file='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$defaults.css', symbol='mx.skins.cursor.DragReject', _line='642')]
    private static var _embed_css_Assets_swf_mx_skins_cursor_DragReject_1231064654:Class;
    [Embed(_pathsep='true', _resolvedSource='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', source='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', _file='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$defaults.css', symbol='mx.skins.cursor.DragLink', _line='640')]
    private static var _embed_css_Assets_swf_mx_skins_cursor_DragLink_505798119:Class;
    [Embed(_pathsep='true', _resolvedSource='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', source='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', _file='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$defaults.css', symbol='mx.skins.cursor.DragMove', _line='641')]
    private static var _embed_css_Assets_swf_mx_skins_cursor_DragMove_505766336:Class;
    [Embed(_pathsep='true', _resolvedSource='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', source='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', _file='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$defaults.css', symbol='mx.skins.cursor.DragCopy', _line='638')]
    private static var _embed_css_Assets_swf_mx_skins_cursor_DragCopy_505536108:Class;

    public static function init(fbs:IFlexModuleFactory):void
    {
		var style:CSSStyleDeclaration = StyleManager.getStyleDeclaration("DragManager");
    
        if (!style)
		{
			style = new CSSStyleDeclaration();
			StyleManager.setStyleDeclaration("DragManager", style, false);
		}
    
        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.copyCursor = _embed_css_Assets_swf_mx_skins_cursor_DragCopy_505536108;
                this.defaultDragImageSkin = mx.skins.halo.DefaultDragImage;
                this.linkCursor = _embed_css_Assets_swf_mx_skins_cursor_DragLink_505798119;
                this.moveCursor = _embed_css_Assets_swf_mx_skins_cursor_DragMove_505766336;
                this.rejectCursor = _embed_css_Assets_swf_mx_skins_cursor_DragReject_1231064654;
            };
        }
    }
}

}
