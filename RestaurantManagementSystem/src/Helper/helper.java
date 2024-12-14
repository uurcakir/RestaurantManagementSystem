package Helper;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class helper {

	public static void optionPaneChangeButtonText() {
		UIManager.put("OptionPane.cancelButtonText", "İptal");
		UIManager.put("OptionPane.noButtonText", "Hayır");
		UIManager.put("OptionPane.okButtonText", "Tamam");
		UIManager.put("OptionPane.yesButtonText", "Evet");
	}

	public static void showError(String str) {
		String msg;

		optionPaneChangeButtonText();

		switch (str) {
		case "fill":
			msg = "Lütfen tüm alanları doldurun.";
			break;
		case "wrong":
			msg = "Girdiğiniz bilgilerin doğruluğunu kontrol edin.";
			break;
		default:
			msg = str;
		}

		JOptionPane.showMessageDialog(null, msg, "HATA", JOptionPane.ERROR_MESSAGE);
	}

	public static boolean showConfirm(String str) {
		String msg;
		optionPaneChangeButtonText();

		switch (str) {
		case "sure":
			msg = "Bu işlemi gerçekleştirmek istediğinize emin misiniz?";
			break;
		default:
			msg = str;
			break;
		}

		int res = JOptionPane.showConfirmDialog(null, msg, "DİKKAT", JOptionPane.YES_NO_OPTION);

		return res == JOptionPane.YES_OPTION;
	}

	public static void showMessage(String str) {
		String msg;

		optionPaneChangeButtonText();

		switch (str) {
		case "success":
			msg = "İşlem Başarılı.";
			break;

		default:
			msg = str;
		}

		JOptionPane.showMessageDialog(null, msg, "BİLGİ", JOptionPane.INFORMATION_MESSAGE);
	}

}
