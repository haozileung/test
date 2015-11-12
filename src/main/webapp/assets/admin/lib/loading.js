var bload = {
	init : function(tomask, options) {
		var defaults = {
			fadeInSpeed : 300,
			maskOpacity : .4,
			imagePath : false,
			imagePadding : 16,
			imageDims : {
				w : 32,
				h : 32
			},
			fullScreen : false,
			overlay : {
				show : false,
				color : '#000',
				opacity : .2
			}
		};

		if ($.isFunction(options)) {
			options = defaults;
		} else {
			var options = $.extend(true, defaults, options);
		}

		if (tomask.data('bloadInit') === true) {
			var bloadInst = tomask.data('bload');
		} else {
			tomask.data('bloadInit', true);
			var bloadInst = Object.create(bload);
			tomask.data('bload', bloadInst);
		}
		this.$tomask = tomask;
		this.options = options;
	},
	showing : false,
	show : function() {
		var base = this;

		if (base.showing == true)
			return;

		// simple / image
		var pos = base.$tomask.offset();
		if (base.options.fullScreen == false) {
			var left = pos.left
					+ ((base.$tomask.width() - (base.options.imageDims.w + (base.options.imagePadding * 2))) / 2);
			var top = pos.top
					+ ((base.$tomask.height() - (base.options.imageDims.h + (base.options.imagePadding * 2))) / 2);
			var maskPosition = 'absolute';
		} else {
			var left = (($(window).width() - (base.options.imageDims.w + (base.options.imagePadding * 2))) / 2);
			var top = (($(window).height() - (base.options.imageDims.h + (base.options.imagePadding * 2))) / 2);
			var maskPosition = 'fixed';
		}
		var maskCss = {
			padding : base.options.imagePadding + 'px',
			backgroundColor : '#000',
			position : maskPosition,
			borderRadius : '4px 4px 4px 4px',
			top : top,
			left : left,
			zIndex : 10000,
		};

		base.$mask = $('<div />').css(maskCss);

		if (base.options.imagePath === false) {
			base.$mask.append($('<div />').addClass('bloading'));
		} else {
			base.$mask.append($('<div />').css({
				backgroundImage : 'url(' + base.options.imagePath + ')',
				width : base.options.imageDims.w + 'px',
				height : base.options.imageDims.h + 'px'
			}));
		}
		base.showing = true;

		// create/append full overlay
		if (base.options.overlay.show === true) {
			var css = {
				position : 'fixed',
				top : 0,
				left : 0,
				width : '100%',
				height : '100%',
				backgroundColor : base.options.overlay.color,
				zIndex : 9999,
			};

			if (base.options.fullScreen == false) {
				css.position = 'absolute';
				css.top = pos.top + 'px';
				css.left = pos.left + 'px';
				css.width = base.$tomask.width() + 'px';
				css.height = base.$tomask.height() + 'px';
			}

			base.$overlay = $('<div />').css(css);
			$("body").append(
					base.$overlay.fadeTo(base.options.fadeInSpeed,
							base.options.overlay.opacity));
		}
		// append image
		$("body").append(
				base.$mask.fadeTo(base.options.fadeInSpeed,
						base.options.maskOpacity));
	},

	hide : function() {
		this.showing = false;
		this.$mask.remove();
		if (this.options.overlay.show == true)
			this.$overlay.remove();
	}
};
bload.init($('#loading'), {
	imagePath : '/assets/images/bloading.gif',
	imagePadding : 4,
	maskOpacity : .5,
	fullScreen : true,
	overlay : {
		show : true
	}
});
module.exports = ajax;