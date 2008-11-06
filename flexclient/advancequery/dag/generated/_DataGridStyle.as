package 
{

import flash.display.Sprite;
import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.halo.DataGridSortArrow;
import mx.skins.halo.DataGridHeaderSeparator;
import mx.skins.halo.DataGridColumnDropIndicator;
import mx.skins.halo.DataGridColumnResizeSkin;

[ExcludeClass]

public class _DataGridStyle
{
    [Embed(_pathsep='true', _resolvedSource='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', source='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', _file='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$defaults.css', symbol='cursorStretch', _line='520')]
    private static var _embed_css_Assets_swf_cursorStretch_2118006141:Class;

    public static function init(fbs:IFlexModuleFactory):void
    {
		var style:CSSStyleDeclaration = StyleManager.getStyleDeclaration("DataGrid");
    
        if (!style)
		{
			style = new CSSStyleDeclaration();
			StyleManager.setStyleDeclaration("DataGrid", style, false);
		}
    
        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.alternatingItemColors = [0xf7f7f7, 0xffffff];
                this.headerColors = [0xffffff, 0xe6e6e6];
                this.verticalGridLineColor = 0xcccccc;
                this.columnDropIndicatorSkin = mx.skins.halo.DataGridColumnDropIndicator;
                this.columnResizeSkin = mx.skins.halo.DataGridColumnResizeSkin;
                this.headerDragProxyStyleName = "headerDragProxyStyle";
                this.headerSeparatorSkin = mx.skins.halo.DataGridHeaderSeparator;
                this.headerStyleName = "dataGridStyles";
                this.sortArrowSkin = mx.skins.halo.DataGridSortArrow;
                this.stretchCursor = _embed_css_Assets_swf_cursorStretch_2118006141;
            };
        }
    }
}

}
