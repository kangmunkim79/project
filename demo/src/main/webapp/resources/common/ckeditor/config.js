/**
 * @license Copyright (c) 2003-2019, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.enterMode = CKEDITOR.ENTER_BR;
	config.font_names = '맑은 고딕;굴림;돋움;바탕;궁서;나눔손글씨 붓/Nanum Brush Script;나눔손글씨 펜/Nanum Pen Script;제주한라산/Jeju Hallasan;노토산스 한글/Noto Sans KR;Paytone One;Work Sans;Akronim;Allura;Macondo;Indie Flower;Dancing Script;' + CKEDITOR.config.font_names;
	config.font_defaultLabel = 'Noto Sans KR';
};
