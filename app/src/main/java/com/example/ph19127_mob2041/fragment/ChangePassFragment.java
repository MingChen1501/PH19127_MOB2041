package com.example.ph19127_mob2041.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ph19127_mob2041.R;
import com.example.ph19127_mob2041.dao.ThuThuDAO;
import com.example.ph19127_mob2041.model.ThuThu;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;


public class ChangePassFragment extends Fragment {

    private static final String ARG_PARAM1 = "idThuTHu";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = ChangePassFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ThuThuDAO thuThuDAO;
    private ThuThu thuThu;

    private TextInputLayout textInputLayoutPasswordOld;
    private TextInputLayout textInputLayoutPasswordNew;
    private TextInputLayout textInputLayoutPasswordNewConfirm;
    private MaterialButton materialButtonUpdatePassword;
    private MaterialButton materialButtonRefeshTextField;

    public ChangePassFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        thuThuDAO = new ThuThuDAO(getActivity().getApplicationContext());

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findView(view);

        materialButtonUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwOld, pwNew, pwNewConfirm;
                pwOld = textInputLayoutPasswordOld.getEditText().getText().toString();
                pwNew = textInputLayoutPasswordNew.getEditText().getText().toString();
                pwNewConfirm = textInputLayoutPasswordNewConfirm.getEditText().getText().toString();
                textInputLayoutPasswordOld.setErrorEnabled(false);
                textInputLayoutPasswordNew.setErrorEnabled(false);
                textInputLayoutPasswordNewConfirm.setErrorEnabled(false);
                Log.d(TAG,pwOld + pwNew + pwNewConfirm);
                thuThu = thuThuDAO.getById(mParam1);
                Log.d(TAG, "onCLick: " + thuThu.toString());
                if (validPassword(pwOld, pwNew, pwNewConfirm)) {
                    thuThu.setPassword(pwNew);
                    thuThuDAO.update(thuThu);
                    Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    refeshTextField();
                }
                
            }

            private boolean validPassword(String pwOld, String pwNew, String pwNewConfirm) {
                boolean valid = true;
                if (pwOld.isEmpty()) {
                    textInputLayoutPasswordOld.setError("Không được để trống");
                    valid = false;
                } else if (!pwOld.equals(thuThu.getPassword())) {
                    textInputLayoutPasswordOld.setError("Mật khẩu cũ không đúng");
                    valid = false;
                }

                if (pwNew.isEmpty()) {
                    textInputLayoutPasswordNew.setError("Không được để trống");
                    valid = false;
                } else if (pwNew.equals(pwOld)) {
                    textInputLayoutPasswordNew.setError("mật khẩu mới không được giống mật khẩu cũ");
                    valid = false;
                }

                if (pwNewConfirm.isEmpty()) {
                    textInputLayoutPasswordNewConfirm.setError("Không được để trống");
                    valid = false;
                } else if (!pwNew.equals(pwNewConfirm)) {
                    textInputLayoutPasswordNewConfirm.setError("mật khẩu xác nhận khác mật khẩu mới");
                    valid = false;
                }
                return valid;
            }
        });
        materialButtonRefeshTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refeshTextField();
            }
        });
    }

    private void refeshTextField() {
        textInputLayoutPasswordOld.setErrorEnabled(false);
        textInputLayoutPasswordNew.setErrorEnabled(false);
        textInputLayoutPasswordNewConfirm.setErrorEnabled(false);
        textInputLayoutPasswordOld.getEditText().setText("");
        textInputLayoutPasswordNew.getEditText().setText("");
        textInputLayoutPasswordNewConfirm.getEditText().setText("");
    }

    private void findView(@NonNull View view) {
        textInputLayoutPasswordOld = view.findViewById(R.id.et_frmChangePass_oldPw);
        textInputLayoutPasswordNew = view.findViewById(R.id.et_frmChangePass_newPw);
        textInputLayoutPasswordNewConfirm = view.findViewById(R.id.et_frmChangePass_newPwConfirm);
        materialButtonUpdatePassword = view.findViewById(R.id.btn_frmChangePass_change);
        materialButtonRefeshTextField = view.findViewById(R.id.btn_frmChangePass_cancel);
    }

    @NonNull
    public static ChangePassFragment newInstance(String param1, String param2) {
        ChangePassFragment fragment = new ChangePassFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        Log.d(TAG, "newInstance: " + param1 + param2);
        //TODO nghiên cứu args.putParcelable()
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_pass, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}